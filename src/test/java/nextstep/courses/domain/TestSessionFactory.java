package nextstep.courses.domain;

import static nextstep.courses.domain.SessionStatus.ENROLLING;
import static nextstep.courses.factory.CoverImageConverter.toImage;
import static nextstep.courses.factory.SessionPeriodConverter.toSessionPeriod;

import java.time.LocalDate;

public class TestSessionFactory {
    private TestSessionFactory() {
    }

    static Session createTestSession() {
        return session(0, new Enrollments(ENROLLING));
    }

    static Session createTestSession2() {
        return session(0, ENROLLING);
    }

    private static Session session(int charge, SessionStatus sessionStatus) {
        return new Session(0L, new Charge(charge), sessionStatus, new EnrollmentsFactory(),
                toImage("test", 300, 200, 100, ImageType.JPEG), toSessionPeriod(LocalDate.now(), LocalDate.now()));
    }

    public static Session createTestSession2(int charge, int capacity) {
        return paidSession(charge, capacity, ENROLLING);
    }

    private static Session paidSession(int charge, int capacity, SessionStatus sessionStatus) {
        return new PaidSession(0L, new Charge(charge), new Capacity(capacity), sessionStatus, new EnrollmentsFactory(),
                toImage("test", 300, 200, 100, ImageType.JPEG), toSessionPeriod(LocalDate.now(), LocalDate.now()));
    }

    static Session createTestSession(int charge) {
        return session(charge, new LimitedEnrollments(new Capacity(2), ENROLLING));
    }

    private static Session session(int charge, Enrollments enrollments) {
        return new Session(0L, new Charge(charge), enrollments,
                toImage("test", 300, 200, 100, ImageType.JPEG), toSessionPeriod(LocalDate.now(), LocalDate.now()));
    }
}
