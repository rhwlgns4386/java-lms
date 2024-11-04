package nextstep.courses.domain.session.enrollment;

public enum Status {
    PREPARE, RECRUIT, CLOSE;

    public boolean isRecruit() {
        return this == RECRUIT;
    }
}
