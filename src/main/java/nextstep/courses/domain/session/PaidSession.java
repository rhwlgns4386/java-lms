package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class PaidSession extends Session {

    protected PaidSession(String name, CoverImage coverImage, SessionState sessionState, int maxEnrollment,
                          long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        super(name, coverImage, maxEnrollment, sessionState, sessionFee, startDate, endDate);
    }

    protected PaidSession(Long id, String name, CoverImage coverImage, SessionState sessionState, int maxEnrollment,
                          long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, name, coverImage, maxEnrollment, sessionState, sessionFee, startDate, endDate);
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return sessionInfo.isValidPayment(payment, id);
    }
}
