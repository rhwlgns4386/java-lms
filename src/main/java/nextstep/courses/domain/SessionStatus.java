package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING,
    RECRUITING,
    CLOSE;

    public boolean isOpen() {
        return this == RECRUITING;
    }
}
