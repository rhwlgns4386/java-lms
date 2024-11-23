package nextstep.studentsessions.domain;

import java.util.Optional;

public interface StudentSessionRepository {
    Optional<StudentSession> findBySessionIdAndStudentId(Long sessionId, Long studentId);

}
