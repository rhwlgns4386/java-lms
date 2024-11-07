package nextstep.session.domain;

public enum SessionEnrollmentStatus {
    NOT_RECRUITING,
    RECRUITING;

    public boolean isOpenForEnrollment() {
        return RECRUITING == this;
    }
}
