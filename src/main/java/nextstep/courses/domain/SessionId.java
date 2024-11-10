package nextstep.courses.domain;

import java.util.Objects;

public class SessionId {
    private final long sessionId;


    public SessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        return sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId1 = (SessionId) o;
        return sessionId == sessionId1.sessionId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessionId);
    }
}
