package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    protected FreeSession(CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, sessionState, startDate, endDate);
    }

    protected FreeSession(CoverImage coverImage, SessionState sessionState, int enrollment,
                          LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, sessionState, enrollment, startDate, endDate);
    }

    protected FreeSession(Long id, CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        this(id, coverImage, sessionState, INIT_ENROLLMENT, startDate, endDate);
    }

    protected FreeSession(Long id, CoverImage coverImage, SessionState sessionState, int enrollment,
                          LocalDateTime startDate, LocalDateTime endDate) {
        super(id, coverImage, Enrollment.INFINITE_ENROLLMENT, enrollment, sessionState, startDate, endDate);
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return true;
    }

    @Override
    public long getSessionFee() {
        return 0;
    }
}
