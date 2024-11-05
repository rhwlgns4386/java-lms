package nextstep.courses.domain.session;

public enum SessionState {
    PREPARING,
    RECRUITING,
    CLOSED;

    public boolean isOpen() {
        return this == RECRUITING;
    }

}
