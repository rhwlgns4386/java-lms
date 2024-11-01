package nextstep.courses.domain;

public interface StudentRepository {
    int save(Student student, Long sessionId);

    Student findById(Long nsUserId, Long sessionId);
}
