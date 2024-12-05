package nextstep.courses.domain;

import static nextstep.courses.domain.SessionStatus.PROGRESS;
import static nextstep.courses.factory.SessionPeriodConverter.toSessionPeriod;

import java.time.LocalDate;
import java.util.List;

public class TestSessionFactory {
    private TestSessionFactory() {
    }

    static Session createTestSession() {
        return createTestSession(0, PROGRESS);
    }

    public static Session createTestSession(int charge, int capacity) {
        return paidSession(charge, capacity, PROGRESS);
    }

    private static Session createTestSession(int charge, SessionStatus sessionStatus) {
        return session(charge, new EnrollmentsInfo(new EnrollmentsFactory(), sessionStatus));
    }


    private static Session paidSession(int charge, int capacity, SessionStatus sessionStatus) {
        EnrollmentsInfo limitEnrollmentsInfo = new LimitEnrollmentsInfo(new Capacity(capacity),
                new EnrollmentsFactory(),
                sessionStatus);
        return session(charge, limitEnrollmentsInfo);
    }

    private static Session session(int charge, EnrollmentsInfo enrollmentsInfo) {
        return new Session(0L, new Charge(charge), enrollmentsInfo, new CoverImages(List.of()),
                toSessionPeriod(LocalDate.now(), LocalDate.now()));
    }
}
