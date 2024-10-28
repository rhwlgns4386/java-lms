package nextstep.courses.domain.session;

import nextstep.courses.dto.SessionPaymentInfo;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Session {

    static final int NOT_ASSIGNED = -1;

    protected Long id;
    protected SessionInfo sessionInfo;
    protected Enrollment enrollment;
    protected SessionDuration sessionDuration;

    protected Session(String title, CoverImage coverImage, int maxEnrollment, SessionState sessionState,
                      long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, title, coverImage, maxEnrollment, sessionState, sessionFee, startDate, endDate);
    }

    protected Session(Long id, String title, CoverImage coverImage, int maxEnrollment,
                      SessionState sessionState, long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.sessionInfo = new SessionInfo(title, coverImage, sessionFee, sessionState);
        this.enrollment = new Enrollment(maxEnrollment);
        this.sessionDuration = new SessionDuration(startDate, endDate);
    }

    public final SessionPaymentInfo preCheckForRegister() {
        sessionInfo.checkIsOpenSession();
        enrollment.validateAvailability();
        return sessionInfo.sessionPaymentInfo(id);
    }

    public boolean finalizeSessionRegistration(Payment payment) {
        if (isValidPayment(payment)) {
            enrollment.register();
            return true;
        }
        throw new IllegalStateException("결제 내역(금액 등)과 강의 수강 조건이 일치하지 않습니다");
    }

    protected abstract boolean isValidPayment(Payment payment);

    public boolean isSameId(Payment payment) {
        return payment.isSameSessionId(id);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
