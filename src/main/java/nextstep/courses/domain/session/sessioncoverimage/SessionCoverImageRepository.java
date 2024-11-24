package nextstep.courses.domain.session.sessioncoverimage;

import java.util.List;

public interface SessionCoverImageRepository {
    SessionCoverImage findById(long coverImageId);

    List<SessionCoverImage> findBySessionId(long sessionId);
}
