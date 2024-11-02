package nextstep.courses.domain.session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SessionRegistrationEntity {
    private final Long sessionId;
    private final Long userId;
    private final LocalDateTime registeredAt;

    public SessionRegistrationEntity(Long sessionId, Long userId, LocalDateTime registeredAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.registeredAt = registeredAt;
    }

    public static List<SessionRegistrationEntity> fromSession(DefaultSession session) {
        return session.getRegisteredStudentIds()
                .stream()
                .map(userId -> new SessionRegistrationEntity(session.getId(), userId, LocalDateTime.now()))
                .collect(Collectors.toList());
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
