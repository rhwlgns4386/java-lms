package nextstep.courses.domain.port;

import nextstep.courses.domain.image.SessionCoverImage;

public interface SessionCoverImageRepository {

    SessionCoverImage findById(Long id);

    Long save(SessionCoverImage sessionCoverImage);
}
