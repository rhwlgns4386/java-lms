package nextstep.courses.domain;

public enum SessionState {
    READY, OPEN, CLOSE;
    public boolean isOpen() {
        return this == OPEN;
    }
}
