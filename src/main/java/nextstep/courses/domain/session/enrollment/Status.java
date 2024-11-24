package nextstep.courses.domain.session.enrollment;

public enum Status {
    PREPARE, PROGRESS, CLOSE;

    public boolean isProgress() {
        return this == PROGRESS;
    }
}
