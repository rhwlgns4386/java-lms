package nextstep.courses.domain;

public class FreeSession extends Session {

    public FreeSession(SessionId sessionId, SessionDate sessionDate, Image image) {
        super(image, sessionDate, sessionId, SessionStatus.PREPARING, SessionType.FREE);
    }

    public SessionId getSessionId() {
        return super.getSessionId();
    }

    @Override
    protected void register(Registration registration) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }
    }

    public void register() {
        register(null);
    }


}
