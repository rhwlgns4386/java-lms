package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionOrder;
import nextstep.courses.domain.Students;
import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

public interface SessionRepository {
    int saveRegisterSession(Session session);

    Session findSessionInfoById(long id);

}
