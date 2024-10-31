package nextstep.sessions.domain;

import java.util.List;
import java.util.Optional;

public interface ApplicationDetailRepository {
    int save(ApplicationDetail applicationDetail);

    Optional<ApplicationDetail> findByUserAndSession(Long sessionId, Long nsUserId);

    List<ApplicationDetail> findBySession(Long sessionId);
}
