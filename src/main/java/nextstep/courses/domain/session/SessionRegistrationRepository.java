package nextstep.courses.domain.session;

import java.util.List;
import java.util.Optional;

public interface SessionRegistrationRepository {
    void saveRegistrations(Long sessionId, List<Long> userIds);
    void update(SessionRegistration registration);

    Optional<SessionRegistration> findBySessionIdAndUserId(Long sessionId, Long userId);
    List<Long> findRegisteredUserIds(Long sessionId); // 승인된 수강생만 반환


}
