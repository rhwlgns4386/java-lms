package nextstep.courses.domain;

public enum SessionState {
    PREPARING,
    RECRUITING,
    CLOSED;

    public static boolean isOpen(SessionState state) {
        return state == RECRUITING;
    }

}
