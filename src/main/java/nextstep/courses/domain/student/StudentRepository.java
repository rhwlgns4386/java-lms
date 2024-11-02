package nextstep.courses.domain.student;

import java.util.List;

public interface StudentRepository {
    int save(Student student, Long sessionId);

    int[] saveAll(List<Student> students, Long sessionId);

    Student findById(Long nsUserId, Long sessionId);

    List<Student> findAllBySessionId(Long sessionId);
}
