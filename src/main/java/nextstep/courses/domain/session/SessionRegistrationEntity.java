package nextstep.courses.domain.session;

import java.time.LocalDateTime;

public class SessionRegistrationEntity {
    private final Long sessionId;
    private final Long userId;
    private final LocalDateTime registeredAt;

    public SessionRegistrationEntity(Long sessionId, Long userId, LocalDateTime registeredAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.registeredAt = registeredAt;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
