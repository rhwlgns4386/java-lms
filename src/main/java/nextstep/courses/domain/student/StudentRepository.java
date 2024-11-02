package nextstep.courses.domain.student;

public interface StudentRepository {
    int save(Student student, Long sessionId);

    Student findById(Long nsUserId, Long sessionId);
}
