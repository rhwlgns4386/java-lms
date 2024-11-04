package nextstep.sessions.infrastucture;

import nextstep.sessions.domain.FreeSession;
import nextstep.sessions.domain.SessionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("freeSessionRepository")
public class JdbcFreeSessionRepository implements SessionRepository<FreeSession> {
    @Override
    public Optional<FreeSession> findById(Long sessionId) {
        return Optional.empty();
    }
}
