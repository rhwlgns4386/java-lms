package nextstep.courses.domain.session;

import java.util.Objects;

public class SessionStudent {

    private Long id;
    private Long sessionId;
    private Long userId;

    public SessionStudent(Long sessionId, Long userId){
        this.sessionId = sessionId;
        this.userId = userId;
    }


    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionStudent that = (SessionStudent) o;
        return sessionId.equals(that.sessionId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }
}
