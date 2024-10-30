package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class PaidSession extends Session {

    private long sessionFee;

    protected PaidSession(CoverImage coverImage, SessionState sessionState, int maxEnrollment,
                          long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, sessionState, maxEnrollment, sessionFee, startDate, endDate);
    }

    protected PaidSession(Long id, CoverImage coverImage, SessionState sessionState, int maxEnrollment,
                          long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, coverImage, maxEnrollment, sessionState, startDate, endDate);
        this.sessionFee = sessionFee;
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return payment.isValidPayment(id, sessionFee);
    }
}
