package nextstep.courses.domain;

import java.util.List;

public interface SessionStudentRepository {
    int[] saveAll(Students students);

    int[] saveAll(List<SessionStudent> sessionStudents);

    List<SessionStudent> findAllBySessionId(long sessionId);
}
