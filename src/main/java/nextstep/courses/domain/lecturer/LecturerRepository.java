package nextstep.courses.domain.lecturer;

import java.util.Optional;

public interface LecturerRepository {
    int save(Lecturer lecturer, Long sessionId);

    Optional<Lecturer> findByNsUserId(Long userId);

    Optional<Lecturer> findBySessionId(Long sessionId);
}
