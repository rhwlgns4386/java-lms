package nextstep.courses.domain.student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    int save(Student student, Long sessionId);

    int[] saveAll(List<Student> students, Long sessionId);

    Optional<Student> findById(Long nsUserId, Long sessionId);

    List<Student> findAllBySessionId(Long sessionId);
}
