class Ball {

    private final int id;
    private int hitsRemaining;
    private int row;
    private int col;

    Ball(int id, int hits, int row, int col) {
        this.id = id;
        this.hitsRemaining = hits;
        this.row = row;
        this.col = col;
    }

    Ball(Ball other) {
        this.id = other.id;
        this.hitsRemaining = other.getHitsRemaining();
        this.row = other.getRow();
        this.col = other.getCol();
    }

    public int getId() {
        return id;
    }

    public int getHitsRemaining() {
        return this.hitsRemaining;
    }

    public void decrementHits() {
        this.hitsRemaining--;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Ball otherBall) {
            return this.getId() == otherBall.getId()
                    && this.getRow() == otherBall.getRow()
                    && this.getCol() == otherBall.getCol()
                    && this.getHitsRemaining() == otherBall.getHitsRemaining();
        }

        return false;
    }
}
