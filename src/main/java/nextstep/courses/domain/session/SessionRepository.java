package nextstep.courses.domain.session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    long save(Session session, Long courseId);

    Optional<Session> findById(Long id);

    List<Session> findAllByCourseId(Long courseId);
}
