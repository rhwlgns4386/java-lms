package nextstep.courses.domain.session;

public enum SessionStatus {
    READY,
    RECRUITING,
    CLOSED;

    public boolean isRecruiting() {
        return this == RECRUITING;
    }
}
