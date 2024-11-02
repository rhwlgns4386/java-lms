package nextstep.courses.domain.session;

public enum SessionStatus {
    PREPARING,
    RECRUITING,
    CLOSE;

    public boolean isOpen() {
        return this == RECRUITING;
    }
}
