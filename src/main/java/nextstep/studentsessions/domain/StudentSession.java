package nextstep.studentsessions.domain;

import nextstep.global.domain.BaseEntity;
import nextstep.sessions.domain.Session;
import nextstep.users.domain.NsStudent;

import java.time.LocalDateTime;
import java.util.Objects;

public class StudentSession extends BaseEntity {
    private final Session session;
    private final NsStudent student;
    private RegistrationApprovalStatus registrationApprovalStatus;

    public StudentSession(Session session, NsStudent student, LocalDateTime createdAt) {
        this(session, student, RegistrationApprovalStatus.PENDING, createdAt);
    }

    public StudentSession(Session session, NsStudent student, RegistrationApprovalStatus registrationApprovalStatus, LocalDateTime createdAt) {
        this(session, student, registrationApprovalStatus, createdAt, null);
    }

    public StudentSession(Session session, NsStudent student, RegistrationApprovalStatus registrationApprovalStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(0L, createdAt, updatedAt);
        this.session = session;
        this.student = student;
        this.registrationApprovalStatus = registrationApprovalStatus;
    }

    public void reject() {
        this.registrationApprovalStatus = RegistrationApprovalStatus.REJECTED;
    }

    public void approve() {
        this.registrationApprovalStatus = RegistrationApprovalStatus.APPROVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentSession that = (StudentSession) o;
        return Objects.equals(session, that.session) && Objects.equals(student, that.student) && registrationApprovalStatus == that.registrationApprovalStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, student, registrationApprovalStatus);
    }
}
