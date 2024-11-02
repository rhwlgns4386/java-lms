package nextstep.courses.domain;

import nextstep.courses.domain.session.SessionStudent;

import java.util.Optional;

public interface SessionStudentRepository {

    Optional<SessionStudent> findBySessionIdAndNsUserId(Long sessionId, Long nsUserId);

    int save(SessionStudent sessionStudent);
}
