import java.util.*;

class Solution {

    public static void main(String args[]) {
        try {
            final Scanner in = new Scanner(System.in);
            final int mapWidth = in.nextInt();
            final int mapHeight = in.nextInt();
            System.err.println(String.format("Map dimensions : %d,%d", mapWidth, mapHeight));

//            List<String> inputText = List.of("2", "1", "1H");
//            Iterator<String> in = inputText.iterator();
//
//            final int mapWidth = Integer.parseInt(in.next());
//            final int mapHeight = Integer.parseInt(in.next());
//            System.err.println(String.format("Map dimensions : %d,%d", mapWidth, mapHeight));


            GolfMapState initialState = new GolfMapState(mapWidth, mapHeight);
            initializeMap(in, initialState);
            outputMapDebug(initialState);

            List<GolfMapState> openNodes = new ArrayList<>();
            openNodes.add(initialState);
            Backtracking backtracking = new Backtracking();
            GolfMapState solution = backtracking.execute(openNodes, new ArrayList<>());


            outputMap(solution);

        } catch(BacktrackingException e) {
            e.printStackTrace();
            outputMapDebug(e.getSource());
        }
    }

    private static void initializeMap(Iterator<String> in, GolfMapState initialState) {
        final Cell[][] map = initialState.getMap();
        final List<Ball> mapBalls = new ArrayList<>();

        int ballId = 0;
        for (int row = 0; row < map.length; row++) {
            final String inputRow = in.next();

            for(int col = 0; col < inputRow.length(); col++) {
                final char currentChar = inputRow.charAt(col);
                final Cell cell = map[row][col];

                cell.setCharacter(currentChar);

                if(currentChar != 'H' && currentChar != 'X' && currentChar != '.') {
                    final int hitNumber = Integer.parseInt(String.valueOf(currentChar));
                    final Ball newBall = new Ball(ballId++, hitNumber, row, col);

                    mapBalls.add(newBall);
                }
            }

        }

        initialState.setBalls(mapBalls.toArray(Ball[]::new));
    }

    private static void outputMapDebug(GolfMapState state) {
        final Cell[][] map = state.getMap();
        for (int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[0].length; col++) {
                System.err.print(map[row][col].getCharacter());
            }
            System.err.print('\n');
        }
        System.err.print('\n');
    }

    private static void outputMap(GolfMapState state) {
        final Cell[][] map = state.getMap();
        for (int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[0].length; col++) {
                final Cell cell = map[row][col];

                if(cell.isHole() || cell.isWater()) {
                    System.out.print('.');
                } else {
                    System.out.print(map[row][col].getCharacter());
                }
            }
            System.out.print('\n');
        }
    }
}


