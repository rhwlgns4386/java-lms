package nextstep.courses.infrastructure.session;

import nextstep.courses.entity.SessionEntity;

import java.util.List;

public interface SessionRepository {

    SessionEntity findById(Long sessionId);

    int save(SessionEntity session, long courseId);

    List<SessionEntity> findByCourseId(long courseId);
}
