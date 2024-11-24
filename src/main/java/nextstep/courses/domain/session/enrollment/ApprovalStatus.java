package nextstep.courses.domain.session.enrollment;

public enum ApprovalStatus {
    APPROVED,
    DISAPPROVED;

    public static boolean isApproved(String status) {
        return valueOf(status) == APPROVED;
    }

    public static boolean isDisapproved(String status) {
        return valueOf(status) == DISAPPROVED;
    }

}
