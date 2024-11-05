package nextstep.courses.infrastructure;

import nextstep.courses.domain.Students;
import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

public interface SessionRepository {
    int saveRegisterSession(Session session);

    Session findSessionInfoById(long id);

    Students findOrderInfoBySessionId(long l);

    int saveOrderSession(NsUser user, Session session);
}
