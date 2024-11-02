package nextstep.sessions.domain;

import java.util.List;

public interface SessionImageRepository {
    CoverImage save(CoverImage image);
    CoverImage saveWithSessionId(CoverImage image, Long sessionId);
    CoverImage findById(Long imageId);
    List<CoverImage> findAllBySessionId(Long sessionId);

}
