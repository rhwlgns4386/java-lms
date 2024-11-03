package nextstep.courses.domain;

import nextstep.courses.domain.coverimage.SessionCoverImage;

public interface SessionCoverImageRepository {

    int save(SessionCoverImage sessionCoverImage);

    SessionCoverImage findById(Long id);

}
