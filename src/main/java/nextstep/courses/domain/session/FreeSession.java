package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;

public class FreeSession extends DefaultSession {

    public FreeSession(SessionStatus status, SessionPeriod period, CoverImage coverImage) {
        super(status, period, coverImage);
    }

    @Override
    protected void register(Payment payment) {
        validateRegisterStatus();
    }

    public void register() {
        register(null);
    }


}
