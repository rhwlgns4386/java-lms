package nextstep.courses.infrastructure;

import nextstep.courses.domain.EnrollStatus;

import java.util.List;

public interface SessionStudentRepository {
    int save(Long sessionId, List<Long> userId);
    int saveNew(Long sessionId, List<Long> userId, EnrollStatus status);
    int saveNew(Long sessionId, List<Long> approvedStudents, List<Long> applyStudents);
    List<Long> findBySessionId(Long sessionId);
    List<Long> findBySessionIdAndStatus(Long sessionId, EnrollStatus status);
    int update(Long sessionId, List<Long> userId, EnrollStatus status);
}
