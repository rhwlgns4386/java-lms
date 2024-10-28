package nextstep.courses.domain;

import java.util.Objects;

public class FreeSession extends Session {
    private SessionId sessionId;
    private SessionStatus sessionStatus;


    public FreeSession(SessionDate sessionDate, Image image, SessionId sessionId) {
        super(sessionDate, image, SessionType.FREE);
        this.sessionId = sessionId;
        this.sessionStatus = SessionStatus.PREPARING;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FreeSession)) {
            return false;
        }
        FreeSession that = (FreeSession) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessionId);
    }
}
