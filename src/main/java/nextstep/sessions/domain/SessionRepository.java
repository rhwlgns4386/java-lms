package nextstep.sessions.domain;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {

    Optional<Session> findById(Long id);

    List<Session> findByCourseId(Long courseId);

    int save(Session session);

    int modifyStatus(Session session);

    int modifyPeriod(Session session);

    int modifyType(Session session);
}
