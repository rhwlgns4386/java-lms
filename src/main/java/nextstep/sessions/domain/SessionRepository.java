package nextstep.sessions.domain;

import java.util.Optional;

public interface SessionRepository<T extends Session> {
    Optional<T> findById(Long sessionId);
}
