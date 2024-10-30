package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;

public class FreeSession extends DefaultSession {

    public FreeSession(SessionStatus status, SessionPeriod period, CoverImage coverImage) {
        super(status, period, coverImage);
    }

    @Override
    protected void validate(Payment payment) {
        validateSessionStatus();
    }

    @Override
    protected void doRegister(Payment payment) {
        //무료 세선은 수강신청 제한이 없음
    }

    public void register() {
        register(null);
    }


}
