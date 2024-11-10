package nextstep.enrollment.domain;

public enum ApprovalStatus {
    APPROVED("승인"),
    NOT_APPROVED("미승인");

    private final String description;

    ApprovalStatus(String description) {
        this.description = description;
    }
}
