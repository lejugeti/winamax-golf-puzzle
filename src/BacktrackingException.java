class BacktrackingException extends RuntimeException {

    public GolfMapState getSource() {
        return source;
    }

    private GolfMapState source;

    BacktrackingException(String message, GolfMapState source) {
        super(message);
        this.source = source;
    }

    BacktrackingException(String message, Throwable cause, GolfMapState source) {
        super(message, cause);
        this.source = source;
    }
}
