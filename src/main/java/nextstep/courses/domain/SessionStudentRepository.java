package nextstep.courses.domain;

import nextstep.courses.domain.session.SessionStudent;

import java.util.List;

public interface SessionStudentRepository {

    int save(SessionStudent sessionStudent);

    List<SessionStudent> findBySessionId(Long sessionId);
}
