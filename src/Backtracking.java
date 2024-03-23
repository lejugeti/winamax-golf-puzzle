import java.util.List;

class Backtracking {

    GolfMapState execute(List<GolfMapState> openNodes, List<GolfMapState> closedNodes) {
        try {
            if(openNodes.isEmpty()){
                throw new IllegalArgumentException("A solution should have been found");
            }

            final GolfMapState currentNode = openNodes.get(0);
            openNodes.remove(0);

            if(!nodeAlreadyVisited(currentNode, closedNodes)) {
                closedNodes.add(currentNode);

                if(isSolution(currentNode)) {
                    return currentNode;
                }

                openNodes.addAll(0, currentNode.getChildren());
            }

            return execute(openNodes, closedNodes);
        } catch (StackOverflowError e) {
            throw new BacktrackingException("Error while searching solution", e, openNodes.get(0));
        }
    }

    /**
     * Determine if a {@link GolfMapState} is solution by checking if every ball on the map
     * is on a cell with a hole.
     * @param state Current golf map state
     * @return true if the state is solution, false otherwise
     */
    private boolean isSolution(GolfMapState state) {
        final Ball[] balls = state.getBalls();
        final Cell[][] map = state.getMap();
        boolean isSolution = true;

        for(Ball ball: balls) {
            final Cell cellWithBall = map[ball.getRow()][ball.getCol()];

            if(!cellWithBall.isHole()) {
                isSolution = false;
            }
        }

        return isSolution;
    }

    private boolean nodeAlreadyVisited(GolfMapState state, List<GolfMapState> closedNodes) {
        return closedNodes.stream()
                .anyMatch(closed -> closed.equals(state));
    }
}
