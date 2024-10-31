package nextstep.sessions.domain;

import nextstep.registration.domain.SessionRegistrationInfo;

import java.util.List;

public interface SessionRegistrationInfoRepository {
    List<SessionRegistrationInfo> findBySessionId(Long sessionId);

    int save(SessionRegistrationInfo sessionRegistrationInfo);
}
