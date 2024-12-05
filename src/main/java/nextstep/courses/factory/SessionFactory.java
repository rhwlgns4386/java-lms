package nextstep.courses.factory;

import static nextstep.courses.factory.SessionPeriodConverter.toSessionPeriod;

import java.time.LocalDate;
import java.util.List;
import nextstep.courses.domain.Capacity;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.CoverImages;
import nextstep.courses.domain.EnrollmentsFactory;
import nextstep.courses.domain.EnrollmentsInfo;
import nextstep.courses.domain.LimitEnrollmentsInfo;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionId;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;

public class SessionFactory {

    public static Session session(long id, int charge, Integer capacity, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory, List<CoverImage> coverImages,
                                  SessionPeriod sessionPeriod) {
        if (capacity == null) {
            return session(id, new Charge(charge), sessionStatus, enrollmentsFactory, coverImages,
                    sessionPeriod);
        }
        return paidSession(id, new Charge(charge), new Capacity(capacity), sessionStatus, enrollmentsFactory,
                coverImages, sessionPeriod);
    }

    public static Session session(long id, Charge charge, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory,
                                  SessionPeriod sessionPeriod) {
        return new Session(new SessionId(id), charge, sessionStatus, enrollmentsFactory, sessionPeriod);
    }

    public static Session session(long id, Charge charge, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory,
                                  List<CoverImage> coverImages,
                                  SessionPeriod sessionPeriod) {
        return new Session(new SessionId(id), charge, sessionStatus, enrollmentsFactory, coverImages, sessionPeriod);
    }

    public static Session session(long id, int charge, int capacity, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory,
                                  LocalDate startDate,
                                  LocalDate endDate) {
        return paidSession(id, new Charge(charge), new Capacity(capacity), sessionStatus, enrollmentsFactory, List.of(),
                toSessionPeriod(startDate, endDate));
    }

    public static Session paidSession(long id, Charge charge, Capacity capacity, SessionStatus sessionStatus,
                                      EnrollmentsFactory enrollmentsFactory,
                                      List<CoverImage> coverImages, SessionPeriod sessionPeriod) {
        EnrollmentsInfo paidSession = new LimitEnrollmentsInfo(capacity, enrollmentsFactory, sessionStatus);
        return new Session(new SessionId(id), charge, paidSession, new CoverImages(coverImages), sessionPeriod);
    }
}
