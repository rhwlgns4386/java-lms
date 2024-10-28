package nextstep.courses.domain;

public class FreeSession extends DefaultSession {

    public FreeSession(SessionStatus status, SessionPeriod period) {
        super(status, period);
    }

    @Override
    protected void register(Money amount) {
        validateRegisterStatus();
    }
}
