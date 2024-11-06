package nextstep.courses.domain.session;

import java.util.List;
import java.util.Optional;

public interface SessionRegistrationRepository {
    void saveRegistrations(Long sessionId, List<Long> userIds);
    void update(SessionRegistration registration);

    Optional<SessionRegistration> findBySessionIdAndUserId(Long sessionId, Long userId);

    List<SessionRegistration> findRegisteredUsers(Long sessionId);


}
