package nextstep.courses.domain.session.entity;

import nextstep.courses.domain.session.enrollment.ApprovalStatus;

public class StudentEntity {
    private final long userId;
    private final long sessionId;
    private final String approvalStatus;

    public StudentEntity(long userId, long sessionId) {
        this(userId, sessionId, ApprovalStatus.DISAPPROVED.name());
    }

    public StudentEntity(long userId, long sessionId, String approvalStatus) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.approvalStatus = approvalStatus;
    }

    public long getUserId() {
        return userId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
            "userId=" + userId +
            ", sessionId=" + sessionId +
            ", approvalStatus='" + approvalStatus + '\'' +
            '}';
    }
}
