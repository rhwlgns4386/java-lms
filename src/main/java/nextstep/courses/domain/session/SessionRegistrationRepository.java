package nextstep.courses.domain.session;

import java.util.List;

public interface SessionRegistrationRepository {
    void saveRegistrations(List<SessionRegistrationEntity> registrations);

    List<Long> findRegisteredUserIds(Long sessionId);
}
