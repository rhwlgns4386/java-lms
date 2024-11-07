package nextstep.enrollment.domain;

import java.time.LocalDateTime;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.Session;
import nextstep.users.domain.NsUser;

public class Enrollment {
    private final Long id;
    private final Long sessionId;
    private final Long nsUserId;
    private final LocalDateTime enrollmentDate;
    private final Payment payment;
    private ApprovalStatus approvalStatus;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment(Long id, Long sessionId, Long nsUserId, LocalDateTime enrollmentDate, Payment payment,
        ApprovalStatus approvalStatus, EnrollmentStatus enrollmentStatus) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.enrollmentDate = enrollmentDate;
        this.payment = payment;
        this.approvalStatus = approvalStatus;
        this.enrollmentStatus = enrollmentStatus;
    }

    private Enrollment(Long id, Long sessionId, Long nsUserId, Payment payment) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.enrollmentDate = LocalDateTime.now();
        this.payment = payment;
        this.approvalStatus = ApprovalStatus.REJECTED;
        this.enrollmentStatus = EnrollmentStatus.ACTIVE;
    }

    public static Enrollment free(Long id, Session session, NsUser nsUser) {
        return new Enrollment(id, session.getId(), nsUser.getId(), null);
    }

    public static Enrollment paid(Long id, Session session, NsUser nsUser, Payment payment) {
        return new Enrollment(id, session.getId(), nsUser.getId(), payment);
    }

    public void cancel() {
        if (approvalStatus == ApprovalStatus.APPROVED) {
            throw new IllegalStateException("수강신청 승인되어 취소할 수 없습니다.");
        }
        enrollmentStatus = EnrollmentStatus.CANCELLED;
    }

    public void approve() {
        approvalStatus = ApprovalStatus.APPROVED;
    }

    public void reject() {
        approvalStatus = ApprovalStatus.REJECTED;
    }

    public Long getPaymentAmount() {
        return payment.getAmount();
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
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
