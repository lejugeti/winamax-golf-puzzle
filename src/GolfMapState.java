import java.util.*;

class GolfMapState {
    private final Cell[][] map;
    private Ball[] balls;

    public Cell[][] getMap() {
        return this.map;
    }

    public Ball[] getBalls() {
        return this.balls;
    }

    public void setBalls(Ball[] balls) {
        this.balls = balls;
    }

    GolfMapState(int width, int height) {
        this.map = new Cell[height][width];

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                map[row][col] = new Cell();
            }
        }
    }

    GolfMapState(GolfMapState otherState) {
        final Cell[][] otherMap = otherState.getMap();
        this.map = new Cell[otherMap.length][otherMap[0].length];

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                map[row][col] = new Cell(otherMap[row][col]);
            }
        }

        this.balls = Arrays.stream(otherState.getBalls())
                .map(Ball::new)
                .toArray(Ball[]::new);
    }

    List<GolfMapState> getChildren() {
        final List<GolfMapState> childrenState = new ArrayList<>();

        for(BallDirection direction: BallDirection.values()) {
            computeNextState(direction, childrenState);
        }

        return childrenState;
    }

    private void computeNextState(final BallDirection direction, final List<GolfMapState> childrenState) {
        final GolfMapState nextState = new GolfMapState(this);
        final Optional<Ball> candidateBall = getBallThatCanMove(nextState);

        if(candidateBall.isPresent()) {
            final Ball ballMoving = candidateBall.get();
            int movementsRemaining = ballMoving.getHitsRemaining();
            int row = ballMoving.getRow();
            int col = ballMoving.getCol();

            while(movementsRemaining > 0) {
                Optional<Coordinates> nextCoordinates = getNextCoordinates(nextState.getMap(), row, col, direction);

                if(nextCoordinates.isPresent()) {
                    final int movementsRemainingNextTurn = movementsRemaining - 1;

                    final Cell currentCell = nextState.getMap()[row][col];
                    currentCell.setCharacter(direction.getCharacter());

                    row = nextCoordinates.get().getRow();
                    col = nextCoordinates.get().getCol();

                    if(travelingIsNotValid(nextState.getMap(), nextState.getBalls(), row, col, movementsRemainingNextTurn)) {
                        break;
                    } else {
                        movementsRemaining = movementsRemainingNextTurn;
                    }
                } else {
                    break;
                }
            }

            // A valid state has been found
            if(movementsRemaining == 0) {
                ballMoving.setRow(row);
                ballMoving.setCol(col);
                ballMoving.decrementHits();

                childrenState.add(nextState);
            }
        }
    }

    private Optional<Ball> getBallThatCanMove(GolfMapState nextState) {
        final Cell[][] map = nextState.getMap();
        final Ball[] balls = nextState.getBalls();

        Optional<Ball> ballThatCanMove = Optional.empty();
        for(Ball ball: balls) {
            final Cell cellWithBall = map[ball.getRow()][ball.getCol()];

            if(ball.getHitsRemaining() != 0 && !cellWithBall.isHole()) {
                ballThatCanMove = Optional.of(ball);
            }
        }

        return ballThatCanMove;
//        throw new BactrackingException("At least one ball should be able to move.", nextState);
    }

    private Optional<Coordinates> getNextCoordinates(Cell[][] map, int row, int col, BallDirection direction) {
        Optional<Coordinates> nextCoordinates = Optional.empty();
        int nextRow = row;
        int nextCol = col;
        if(direction == BallDirection.LEFT) {
            nextCol--;
        } else if(direction == BallDirection.RIGHT) {
            nextCol++;
        } else if(direction == BallDirection.UP) {
            nextRow--;
        } else if(direction == BallDirection.DOWN) {
            nextRow++;
        } else {
            throw new IllegalArgumentException("Direction " + direction + " is not valid");
        }

        if(0 <= nextRow && nextRow < map.length
                && 0 <= nextCol && nextCol < map[nextRow].length) {
            nextCoordinates = Optional.of(new Coordinates(nextRow, nextCol));
        }

        return nextCoordinates;
    }

    private boolean travelingIsNotValid(Cell[][] map, Ball[] balls, int nextRow, int nextCol, int movementRemainingNextTurn) {
        final Cell nextCell = map[nextRow][nextCol];

        return nextCell.isArrow()
                || (nextCell.isHole() && movementRemainingNextTurn != 0)
                || (nextCell.isWater() && movementRemainingNextTurn == 0)
                || Arrays.stream(balls).anyMatch(b -> b.getRow() == nextRow && b.getCol() == nextCol);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof GolfMapState otherState) {
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[row].length; col++) {
                    Cell[][] otherMap = otherState.getMap();
                    if(!map[row][col].equals(otherMap[row][col])) {
                        return false;
                    }
                }
            }

            for(Ball ball: getBalls()) {
                final Ball otherBall = Arrays.stream(otherState.getBalls())
                        .filter(b -> ball.getId() == b.getId())
                        .findFirst()
                        .orElseThrow(() -> new BacktrackingException("The two states do not contains the same balls", otherState));

                if(!ball.equals(otherBall)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

}
