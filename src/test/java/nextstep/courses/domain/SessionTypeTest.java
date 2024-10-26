package nextstep.courses.domain;

import nextstep.courses.domain.session.SessionType;

public class SessionTypeTest {
    public static final SessionType FREE_TYPE = SessionType.freeType();
    public static final SessionType PAID_TYPE = SessionType.paidType(1, 1000);


}
