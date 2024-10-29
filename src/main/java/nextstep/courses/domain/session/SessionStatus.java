package nextstep.courses.domain.session;

public enum SessionStatus {
    READY,
    OPEN,
    CLOSED;

    SessionStatus() {
    }

    public boolean isOpen() {
        return this == OPEN;
    }


}
