package nextstep.courses.domain.session;

import nextstep.courses.domain.session.entity.SessionEntity;

public interface SessionRepository {
    int save(SessionEntity sessionEntity);

    SessionEntity findById(long sessionId);

    Session findByIdForSession(long sessionId);

}
