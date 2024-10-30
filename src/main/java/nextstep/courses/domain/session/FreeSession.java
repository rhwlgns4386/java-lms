package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    static final int FREE_SESSION_FEE = 0;

    protected FreeSession(String name, CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        super(name, coverImage, Enrollment.INFINITE_ENROLLMENT, sessionState, FREE_SESSION_FEE, startDate, endDate);
    }

    protected FreeSession(Long id, String name, CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        super(id, name, coverImage, Enrollment.INFINITE_ENROLLMENT, sessionState, FREE_SESSION_FEE, startDate, endDate);
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return true;
    }
}
