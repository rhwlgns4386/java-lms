package nextstep.courses.domain;

import nextstep.courses.domain.cover.CoverImage;

public interface CoverImageRepository {
    int save(Long sessionId, CoverImage coverImage);

    CoverImage findBySessionId(Long sessionId);
}
