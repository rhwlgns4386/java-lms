package nextstep.courses.domain;

public class FreeSession extends Session {
    private SessionStatus sessionStatus;
    private StudentManager studentManager;

    public FreeSession(SessionId sessionId, SessionDate sessionDate, Image image) {
        super(sessionId, sessionDate, image, SessionType.FREE);
        this.sessionStatus = SessionStatus.PREPARING;
        this.studentManager = new StudentManager();
    }

    public SessionId getSessionId() {
        return super.getSessionId();
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }
}
