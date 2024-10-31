package nextstep.sessions.domain;

import java.util.Optional;

public interface SessionRepository {

    Optional<Session> findById(Long id);

    int save(Session session);

    int modifyStatus(Session session);

    int modifyPeriod(Session session);

    int modifyType(Session session);
}
