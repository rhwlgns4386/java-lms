package nextstep.courses.infrastructure.session;

import nextstep.courses.domain.session.Session;

import java.util.List;

public interface SessionRepository {

    Session findById(Long sessionId);

    int save(Session session, long courseId);

    List<Session> findByCourseId(long courseId);
}
