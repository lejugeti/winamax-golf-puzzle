enum BallDirection {
    LEFT('<'),
    RIGHT('>'),
    UP('^'),
    DOWN('v');

    private final char character;

    public char getCharacter() {
        return character;
    }

    BallDirection(char character) {
        this.character = character;
    }
}
