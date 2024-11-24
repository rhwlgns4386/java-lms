package nextstep.courses.domain.session.enrollment;

public enum EnrollmentStatus {
    POSSIBLE,
    IMPOSSIBLE;

    public boolean isPossible() {
        return this == POSSIBLE;
    }

    public boolean isImPossible() {
        return this == IMPOSSIBLE;
    }
}
