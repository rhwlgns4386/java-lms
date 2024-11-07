package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING, RECRUITING, CLOSED;

    public boolean isRecruiting() {
        return this == RECRUITING;
    }

    public boolean isPreparing() {
        return this == PREPARING;
    }
}
