package nextstep.registration.domain;

import java.util.List;
import java.util.Optional;

public interface SessionRegistrationInfoRepository {
    List<SessionRegistrationInfo> findBySessionId(Long sessionId);

    Optional<SessionRegistrationInfo> findBySessionIdAndUserId(Long sessionId, Long userId);

    int save(SessionRegistrationInfo sessionRegistrationInfo);
}
