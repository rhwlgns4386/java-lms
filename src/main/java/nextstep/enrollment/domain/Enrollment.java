package nextstep.enrollment.domain;

import java.time.LocalDateTime;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionType;
import nextstep.users.domain.NsUser;

public class Enrollment {
    private final Long id;
    private final Session session;
    private final Long nsUserId;
    private final LocalDateTime enrollmentDate;
    private final Payment payment;
    private ApprovalStatus approvalStatus;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment(Long id, Session session, Long nsUserId, LocalDateTime enrollmentDate, Payment payment,
        ApprovalStatus approvalStatus, EnrollmentStatus enrollmentStatus) {
        this.id = id;
        this.session = session;
        this.nsUserId = nsUserId;
        this.enrollmentDate = enrollmentDate;
        this.payment = payment;
        this.approvalStatus = approvalStatus;
        this.enrollmentStatus = enrollmentStatus;
    }

    private Enrollment(Long id, Session session, Long nsUserId, Payment payment) {
        this.id = id;
        this.session = session;
        this.nsUserId = nsUserId;
        this.enrollmentDate = LocalDateTime.now();
        this.payment = payment;
        this.approvalStatus = ApprovalStatus.NOT_APPROVED;
        this.enrollmentStatus = EnrollmentStatus.ACTIVE;
    }

    public static Enrollment free(Long id, Session session, NsUser nsUser) {
        if (!session.isRecruiting()) {
            throw new IllegalStateException("모집중인 강의가 아닙니다.");
        }
        return new Enrollment(id, session, nsUser.getId(), null);
    }

    public static Enrollment paid(Long id, Session session, NsUser nsUser, Payment payment) {
        if (!session.isRecruiting()) {
            throw new IllegalStateException("모집중인 강의가 아닙니다.");
        }
        return new Enrollment(id, session, nsUser.getId(), payment);
    }

    // 수강생이 강의를 직접 취소
    public void cancel() {
        if (approvalStatus == ApprovalStatus.APPROVED) {
            throw new IllegalStateException("수강신청 승인되어 취소할 수 없습니다.");
        }
        enrollmentStatus = EnrollmentStatus.CANCELLED;
    }

    // 관리자가 수강 신청을 승인
    public void approve() {
        approvalStatus = ApprovalStatus.APPROVED;
        session.enroll(this);
    }

    public Long getPaymentAmount() {
        return payment.getAmount();
    }

    public Long getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public Long getSessionId() {
        return session.getId();
    }

    public SessionType getSessionType() {
        return session.getSessionType();
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }
}
