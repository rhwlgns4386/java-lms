package nextstep.courses.domain.Lecturer;

import java.util.Optional;

public interface LecturerRepository {
    int save(Lecturer lecturer, Long sessionId);

    Optional<Lecturer> findByNsUserId(Long userId);
}
