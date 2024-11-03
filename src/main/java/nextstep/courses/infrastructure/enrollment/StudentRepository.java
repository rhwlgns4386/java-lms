package nextstep.courses.infrastructure.enrollment;

import nextstep.courses.entity.StudentEntity;

import java.util.List;

public interface StudentRepository {

    int save(StudentEntity studentEntity, long sessionId);

    List<StudentEntity> findBySessionId(long sessionId);

    int update(StudentEntity student);
}
