package nextstep.courses.domain.cover;

import java.util.List;

public interface CoverImageRepository {
    Long save(CoverImage coverImage);

    CoverImage findById(Long id);

    List<CoverImage> findBySessionId(Long id);
}
