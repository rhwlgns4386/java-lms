package nextstep.courses.domain;

import java.util.List;

public interface SessionImageRepository {
    int[] saveAll(Images sessionImages);
    List<SessionImage> findAllBySessionId(long sessionId);
}
