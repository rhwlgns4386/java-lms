package nextstep.courses.factory;

import static nextstep.courses.factory.SessionPeriodConverter.toSessionPeriod;

import java.time.LocalDate;
import java.util.List;
import nextstep.courses.domain.Capacity;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.DefaultEnrollments;
import nextstep.courses.domain.LimitedEnrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;

public class SessionFactory {
    private SessionFactory() {
    }

    public static Session freeSession(SessionStatus sessionStatus, LocalDate startDate, LocalDate endDate) {

        return freeSession(sessionStatus, toSessionPeriod(startDate, endDate));
    }

    public static Session freeSession(SessionStatus sessionStatus, SessionPeriod sessionPeriod) {

        return session(null, Charge.ZERO, new DefaultEnrollments(sessionStatus), List.of(), sessionPeriod);
    }

    public static Session paidSession(int charge, int capacity, SessionStatus sessionStatus, LocalDate startDate,
                                      LocalDate endDate) {

        return paidSession(new Charge(charge), new Capacity(capacity), sessionStatus,
                toSessionPeriod(startDate, endDate));
    }


    public static Session paidSession(Charge charge, Capacity capacity, SessionStatus sessionStatus,
                                      SessionPeriod sessionPeriod) {

        return session(null, charge, new LimitedEnrollments(capacity, sessionStatus), List.of(), sessionPeriod);
    }


    public static Session session(Long id, int charge, DefaultEnrollments enrollments,
                                  List<CoverImage> coverImages, SessionPeriod sessionPeriod) {
        return session(id, new Charge(charge), enrollments, coverImages, sessionPeriod);
    }

    public static Session session(Long id, Charge charge, DefaultEnrollments enrollments,
                                  List<CoverImage> coverImages, SessionPeriod sessionPeriod) {
        return new Session(id, charge, enrollments, coverImages, sessionPeriod);
    }
}
