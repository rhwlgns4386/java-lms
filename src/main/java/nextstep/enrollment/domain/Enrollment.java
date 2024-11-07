package nextstep.enrollment.domain;

import java.time.LocalDateTime;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.Session;
import nextstep.users.domain.NsUser;

public class Enrollment {
    private final Long id;
    private final Session session;
    private final NsUser nsUser;
    private final LocalDateTime enrollmentDate;
    private final Payment payment;
    private ApprovalStatus approvalStatus;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment(Long id, Session session, NsUser nsUser, LocalDateTime enrollmentDate, Payment payment,
        ApprovalStatus approvalStatus, EnrollmentStatus enrollmentStatus) {
        this.id = id;
        this.session = session;
        this.nsUser = nsUser;
        this.enrollmentDate = enrollmentDate;
        this.payment = payment;
        this.approvalStatus = approvalStatus;
        this.enrollmentStatus = enrollmentStatus;
    }

    private Enrollment(Long id, Session session, NsUser nsUser, Payment payment) {
        this.id = id;
        this.session = session;
        this.nsUser = nsUser;
        this.enrollmentDate = LocalDateTime.now();
        this.payment = payment;
        this.approvalStatus = ApprovalStatus.REJECTED;
        this.enrollmentStatus = EnrollmentStatus.ACTIVE;
    }

    public static Enrollment free(Long id, Session session, NsUser nsUser) {
        return new Enrollment(id, session, nsUser, null);
    }

    public static Enrollment paid(Long id, Session session, NsUser nsUser, Payment payment) {
        return new Enrollment(id, session, nsUser, payment);
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

    public Session getSession() {
        return session;
    }

    public NsUser getNsUser() {
        return nsUser;
    }

    public Long getSessionId() {
        return session.getId();
    }

    public Long getNsUserId() {
        return nsUser.getId();
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
