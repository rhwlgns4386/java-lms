package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    static final int FREE_SESSION_FEE = 0;

    protected FreeSession(CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, sessionState, startDate, endDate);
    }

    protected FreeSession(Long id, CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        super(id, coverImage, Enrollment.INFINITE_ENROLLMENT, sessionState, startDate, endDate);
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return true;
    }
}
