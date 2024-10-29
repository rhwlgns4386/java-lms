package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeSession extends Session {

    public FreeSession(SessionId sessionId, SessionDate sessionDate, Image image) {
        super(image, sessionDate, sessionId, SessionStatus.PREPARING, SessionType.FREE);
    }

    public SessionId getSessionId() {
        return super.getSessionId();
    }

    @Override
    protected void register(Payment payment) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }
    }

    @Override
    protected void open() {
        super.updateStatus(SessionStatus.RECRUITING);
    }

    @Override
    protected void close() {
        super.updateStatus(SessionStatus.CLOSE);
    }

    public void register() {
        register(null);
    }


}
