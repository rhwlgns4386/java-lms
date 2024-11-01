package nextstep.courses.domain;

import nextstep.courses.domain.cover.CoverImage;

public interface CoverImageRepository {
    int save(CoverImage coverImage);

    CoverImage findById(Long id);
}
