class Coordinates {
    private int row;
    private int col;

    Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    Coordinates(Coordinates other) {
        this.row = other.row;
        this.col = other.col;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}
