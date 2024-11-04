package nextstep.sessions.infrastucture;

import nextstep.sessions.domain.PaidSession;
import nextstep.sessions.domain.SessionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("paidSessionRepository")
public class JdbcPaidSessionRepository implements SessionRepository<PaidSession> {
    @Override
    public Optional<PaidSession> findById(Long sessionId) {
        return Optional.empty();
    }
}
