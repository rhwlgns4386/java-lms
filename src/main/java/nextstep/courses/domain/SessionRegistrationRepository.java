package nextstep.courses.domain;

import nextstep.courses.domain.session.DefaultSession;
import nextstep.courses.domain.session.FreeSession;
import nextstep.courses.domain.session.PaidSession;
import nextstep.courses.domain.session.SessionRegistrationEntity;

import java.util.List;
import java.util.Set;

public interface SessionRegistrationRepository {
    void saveRegistrations(List<SessionRegistrationEntity> registrations);
    List<Long> findRegisteredUserIds(Long sessionId);
}
