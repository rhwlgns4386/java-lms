package nextstep.courses.domain;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    int save(Student student);

    Optional<Student> findById(Long id);

    List<Student> findBySessionId(Long sessionId);
}
