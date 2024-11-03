package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImages;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class PaidSession extends Session {

    private long sessionFee;

    protected PaidSession(Long id, CoverImage coverImage, CoverImages coverImages, SessionState sessionState,
                          RecruitState recruitState, int maxEnrollment, int enrollment, long sessionFee,
                          LocalDateTime startDate, LocalDateTime endDate) {
        super(id, coverImage, coverImages, maxEnrollment, enrollment, sessionState, recruitState, startDate, endDate);
        this.sessionFee = sessionFee;
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return payment.isValidPayment(id, sessionFee);
    }

    @Override
    public long getSessionFee() {
        return sessionFee;
    }
}
