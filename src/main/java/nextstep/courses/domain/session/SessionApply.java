package nextstep.courses.domain.session;

import java.util.Objects;

public class SessionApply {

    private Long id;
    private Long sessionId;
    private Long userId;
    private boolean isGuest;
    private boolean isDeleted;

    public SessionApply(Long id, Long sessionId, Long userId, boolean isGuest, boolean isDeleted) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.isGuest = isGuest;
        this.isDeleted = isDeleted;
    }

    public static SessionApply create(Long sessionId, Long userId) {
        return new SessionApply(0L, sessionId, userId, false, false);
    }


    public void cancel() {
        this.isDeleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionApply that = (SessionApply) o;
        return sessionId.equals(that.sessionId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
