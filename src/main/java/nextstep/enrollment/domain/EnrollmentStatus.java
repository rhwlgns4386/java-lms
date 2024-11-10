package nextstep.enrollment.domain;

public enum EnrollmentStatus {
    ACTIVE("정상"),
    CANCELLED("취소");

    public final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }
}
