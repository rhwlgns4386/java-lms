package nextstep.courses.domain;

import java.util.List;

public interface SessionStudentRepository {
    int[] saveAll(Students students);
    List<SessionStudent> findAllBySessionId(long sessionId);
}
