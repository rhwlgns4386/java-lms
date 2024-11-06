package nextstep.courses.domain;

import java.util.List;

public interface SessionImageRepository {
    int save(SessionImage image);

    SessionImage findById(Long id);

    List<SessionImage> findBySessionId(Long sessionId);
}
