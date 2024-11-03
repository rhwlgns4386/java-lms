package nextstep.courses.domain;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    int save(Session session);
    Optional<Session> findById(Long id);
    List<Session> findByTeacherId(Long teacherId);
}
