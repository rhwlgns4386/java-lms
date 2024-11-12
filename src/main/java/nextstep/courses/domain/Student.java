package nextstep.courses.domain;

import nextstep.courses.NotPendingException;

import java.util.Objects;

public class Student {
    private Long id;
    private Long nsUserId;
    private Long sessionId;
    private SessionApprovalStatus approvalStatus;

    public Student(Long nsUserId, Long sessionId) {
        this(0L, nsUserId, sessionId, SessionApprovalStatus.PENDING);
    }

    public Student(Long id, Long nsUserId, Long sessionId, SessionApprovalStatus approvalStatus) {
        this.id = id;
        this.nsUserId = nsUserId;
        this.sessionId = sessionId;
        this.approvalStatus = approvalStatus;
    }

    public void updateApprovalStatus(SessionApprovalStatus sessionApprovalStatus) {
        if (!approvalStatus.isPending()) {
            throw new NotPendingException("It cannot be approved." + this.approvalStatus);
        }
        this.approvalStatus = sessionApprovalStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public SessionApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(nsUserId, student.nsUserId) && Objects.equals(sessionId, student.sessionId) && approvalStatus == student.approvalStatus;
    }
}
