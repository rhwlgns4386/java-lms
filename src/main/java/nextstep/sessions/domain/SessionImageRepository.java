package nextstep.sessions.domain;

import java.util.Optional;

public interface SessionImageRepository {
    int save(SessionImage sessionImage);

    Optional<SessionImage> findBySession(Long sessionId);
}
