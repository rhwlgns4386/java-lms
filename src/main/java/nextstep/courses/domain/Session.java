package nextstep.courses.domain;

public abstract class Session {
    private SessionId sessionId;
    private SessionDate sessionDate;
    private Image image;
    private SessionType sessionType;

    public Session(SessionDate sessionDate, Image image, SessionType sessionType) {
        this.sessionDate = sessionDate;
        this.image = image;
        this.sessionType = sessionType;
    }

    public Session(SessionId sessionId, SessionDate sessionDate, Image image, SessionType sessionType) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.image = image;
        this.sessionType = sessionType;
    }

    public SessionId getSessionId() {
        return sessionId;
    }
}
