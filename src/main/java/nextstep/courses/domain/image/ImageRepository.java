package nextstep.courses.domain.image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    int save(Image image, Long sessionId);

    Optional<Image> findById(long id);

    Optional<Image> findBySessionId(long sessionId);

    List<Image> findAllBySessionId(long sessionId);
}
