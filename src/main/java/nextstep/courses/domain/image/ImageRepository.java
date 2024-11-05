package nextstep.courses.domain.image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    int save(Image image, Long sessionId);

    int[] saveAll(List<Image> images, Long sessionId);

    Optional<Image> findById(long id);

    List<Image> findAllBySessionId(long sessionId);
}
