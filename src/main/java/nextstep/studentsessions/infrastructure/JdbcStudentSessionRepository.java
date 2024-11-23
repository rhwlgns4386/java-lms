package nextstep.studentsessions.infrastructure;

import nextstep.studentsessions.domain.StudentSession;
import nextstep.studentsessions.domain.StudentSessionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("studentSessionRepository")
public class JdbcStudentSessionRepository implements StudentSessionRepository {
    @Override
    public Optional<StudentSession> findBySessionIdAndStudentId(Long sessionId, Long studentId) {
        return Optional.empty();
    }
}
