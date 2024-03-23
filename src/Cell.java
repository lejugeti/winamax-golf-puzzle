import java.util.Arrays;

class Cell {
    private char character;

    Cell() {}

    Cell(Cell other) {
        this.character = other.getCharacter();
    }

    public char getCharacter() {
        return this.character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }


    boolean isHole() {
        return this.character == 'H';
    }

    boolean isWater() {
        return this.character == 'X';
    }

    boolean isFree() {
        return this.character == '.';
    }

    boolean isArrow() {
        return Arrays.stream(BallDirection.values())
                .anyMatch(direction -> direction.getCharacter() == this.character);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Cell otherCell) {
            return this.getCharacter() == otherCell.getCharacter();
        }

        return false;
    }
}
