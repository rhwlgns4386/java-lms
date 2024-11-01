package nextstep.courses.infrastructure;

import java.util.List;

public interface SessionStudentRepository {
    int save(Long sessionId, List<Long> userId);
    List<Long> findBySessionId(Long sessionId);
}
