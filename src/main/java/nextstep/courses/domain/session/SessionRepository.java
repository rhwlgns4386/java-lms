package nextstep.courses.domain.session;

import java.util.List;

public interface SessionRepository {
    long save(Session session, Long courseId);

    Session findById(Long id);

    List<Session> findAllByCourseId(Long courseId);
}
