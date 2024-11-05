package nextstep.courses.domain;

import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.courses.domain.coverimage.SessionCoverImages;

public interface SessionCoverImageRepository {

    int save(SessionCoverImage sessionCoverImage);

    SessionCoverImage findById(Long id);

    void saveAll(SessionCoverImages sessionCoverImages);
}
