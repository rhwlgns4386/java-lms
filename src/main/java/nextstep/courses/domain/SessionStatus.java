package nextstep.courses.domain;

public enum SessionStatus {
    READY,
    OPEN,
    CLOSED;

    public static SessionStatus fromString(String status) {
        try {
            return SessionStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return READY;
        }
    }
}
