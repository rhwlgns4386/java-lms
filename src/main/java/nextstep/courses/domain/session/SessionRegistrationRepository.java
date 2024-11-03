package nextstep.courses.domain.session;

import java.util.List;

public interface SessionRegistrationRepository {
    void saveRegistrations(Long sessionId, List<Long> userIds);

    List<Long> findRegisteredUserIds(Long sessionId);
}
