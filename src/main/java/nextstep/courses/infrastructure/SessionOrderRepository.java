package nextstep.courses.infrastructure;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionOrder;
import nextstep.courses.domain.Students;
import nextstep.users.domain.NsUser;

public interface SessionOrderRepository {

    Students findOrderInfoBySessionId(long l);

    int saveOrderSession(NsUser user, Session session);

    SessionOrder findSessionOrderByOrderId(long orderId);

    int saveOrderStateSessionOrder(SessionOrder approvedSessionOrder);
}
