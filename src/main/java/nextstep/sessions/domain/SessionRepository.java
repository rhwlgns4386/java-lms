package nextstep.sessions.domain;

import java.util.Optional;

public interface SessionRepository {
    Optional<FreeSession> findFreeSessionById(Long sessionId);
    Optional<PaidSession> findPaidSessionById(Long sessionId);

}
