package nextstep.courses.domain.session;

import nextstep.courses.SessionException;

import java.util.Objects;

public class SessionApply {

    private Long id;
    private Long sessionId;
    private Long userId;
    private boolean isGuest;
    private boolean isSubmit;
    private boolean isDeleted;

    public SessionApply(Long id, Long sessionId, Long userId, boolean isGuest, boolean isDeleted, boolean isSubmit) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.isGuest = isGuest;
        this.isDeleted = isDeleted;
        this.isSubmit = isSubmit;
    }

    public static SessionApply create(Long sessionId, Long userId) {
        return new SessionApply(0L, sessionId, userId, false, false, false);
    }


    public void cancel() {
        if (!isGuest) {
            throw new SessionException("선발된 인원은 수강 취소 할 수 없습니다");
        }
        this.isDeleted = true;
    }


    public void submit() {
        if (isGuest) {
            throw new SessionException("선발된 인원이 아니라 승인할 수 없습니다");
        }

        this.isSubmit = true;
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

    public boolean isSubmit() {
        return isSubmit;
    }

}
