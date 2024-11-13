package nextstep.courses.domain;

public enum SessionApprovalStatus {
    PENDING, APPROVED, REJECTED;

    public boolean isPending() {
        return this == PENDING;
    }
}
