package nextstep.users.domain;

import java.util.Optional;

public interface TeacherRepository {
    int save(NsTeacher teacher);
    Optional<NsTeacher> findById(Long id);
}
