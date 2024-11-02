package nextstep.courses.infrastructure;

import java.util.List;

public interface SessionSessionImageRepository {
    int save(Long sessionId, List<Long> imageId);
    List<Long> findBySessionId(Long sessionId);
}
