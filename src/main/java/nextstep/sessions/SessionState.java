package nextstep.sessions;

public enum SessionState {
    READY, OPEN, CLOSE;
    public boolean isOpen() {
        return this == OPEN;
    }
}
