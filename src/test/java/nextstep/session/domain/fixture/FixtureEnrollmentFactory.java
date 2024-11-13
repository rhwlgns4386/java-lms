package nextstep.session.domain.fixture;

import nextstep.enrollment.domain.Enrollment;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.Session;
import nextstep.users.domain.NsUserTest;

public class FixtureEnrollmentFactory {

    private FixtureEnrollmentFactory() {
    }

    public static Enrollment createEnrollmentByFreeSession(Session session) {
        return Enrollment.free(1L, session, NsUserTest.JAVAJIGI);
    }

    public static Enrollment createEnrollmentByPaidSession(Session session, Payment payment) {
        return Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);
    }
}
