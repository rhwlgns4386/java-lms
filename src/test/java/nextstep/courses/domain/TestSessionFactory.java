package nextstep.courses.domain;

import static nextstep.courses.domain.SessionStatus.ENROLLING;
import static nextstep.courses.factory.SessionPeriodConverter.toSessionPeriod;

import java.time.LocalDate;
import java.util.List;

public class TestSessionFactory {
    private TestSessionFactory() {
    }

    static Session createTestSession() {
        return session(0, new DefaultEnrollments(ENROLLING));
    }

    static Session createTestSession2() {
        return session(0, ENROLLING);
    }

    private static Session session(int charge, SessionStatus sessionStatus) {
        return new Session(0L, new Charge(charge), sessionStatus, new EnrollmentsFactory(),
                toSessionPeriod(LocalDate.now(), LocalDate.now()));
    }

    public static Session createTestSession2(int charge, int capacity) {
        return paidSession(charge, capacity, ENROLLING);
    }

    private static Session paidSession(int charge, int capacity, SessionStatus sessionStatus) {
        return new PaidSession(0L, new Charge(charge), new Capacity(capacity), sessionStatus, new EnrollmentsFactory(),
                List.of(), toSessionPeriod(LocalDate.now(), LocalDate.now()));
    }

    static Session createTestSession(int charge) {
        return session(charge, new LimitedEnrollments(new Capacity(2), ENROLLING));
    }

    private static Session session(int charge, DefaultEnrollments enrollments) {
        return new Session(0L, new Charge(charge), enrollments,
                toSessionPeriod(LocalDate.now(), LocalDate.now()));
    }
}
