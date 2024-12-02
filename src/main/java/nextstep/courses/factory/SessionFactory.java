package nextstep.courses.factory;

import static nextstep.courses.factory.CoverImageConverter.toImage;
import static nextstep.courses.factory.SessionPeriodConverter.toSessionPeriod;

import java.time.LocalDate;
import nextstep.courses.domain.Capacity;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.EnrollmentsFactory;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.LimitedEnrollments;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;

public class SessionFactory {
    private SessionFactory() {
    }

    public static Session freeSession(SessionStatus sessionStatus, String fileName, int width, int height, int size,
                                      ImageType imageType, LocalDate startDate, LocalDate endDate) {

        return freeSession(sessionStatus, toImage(fileName, width, height, size, imageType),
                toSessionPeriod(startDate, endDate));
    }

    public static Session freeSession(SessionStatus sessionStatus, CoverImage coverImage, SessionPeriod sessionPeriod) {

        return session(null, Charge.ZERO, new Enrollments(sessionStatus), coverImage, sessionPeriod);
    }

    public static Session paidSession(int charge, int capacity, SessionStatus sessionStatus, String fileName, int width,
                                      int height, int size, ImageType imageType, LocalDate startDate,
                                      LocalDate endDate) {

        return paidSession(new Charge(charge), new Capacity(capacity), sessionStatus,
                toImage(fileName, width, height, size, imageType), toSessionPeriod(startDate, endDate));
    }


    public static Session paidSession(Charge charge, Capacity capacity, SessionStatus sessionStatus,
                                      CoverImage coverImage, SessionPeriod sessionPeriod) {

        return session(null, charge, new LimitedEnrollments(capacity, sessionStatus), coverImage, sessionPeriod);
    }

    public static Session session(long id, int charge, Enrollments enrollments, String fileName, int width, int height,
                                  int size, ImageType imageType, LocalDate startDate, LocalDate endDate) {
        return session(id, new Charge(charge), enrollments, toImage(fileName, width, height, size, imageType),
                toSessionPeriod(startDate, endDate));
    }

    public static Session session(long id, int charge, Integer capacity, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory, String fileName,
                                  int width, int height, int size, ImageType imageType, LocalDate startDate,
                                  LocalDate endDate) {
        if (capacity == null) {
            return session(id, new Charge(charge), sessionStatus, enrollmentsFactory, toImage(fileName, width, height,
                            size, imageType),
                    toSessionPeriod(startDate, endDate));
        }
        return paidSession(id, new Charge(charge), new Capacity(capacity), sessionStatus, enrollmentsFactory,
                toImage(fileName, width,
                        height, size, imageType), toSessionPeriod(startDate, endDate));
    }

    public static Session session(long id, int charge, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory,
                                  String fileName, int width,
                                  int height, int size, ImageType imageType, LocalDate startDate, LocalDate endDate) {
        return session(id, new Charge(charge), sessionStatus, enrollmentsFactory, toImage(fileName, width, height,
                        size, imageType),
                toSessionPeriod(startDate, endDate));
    }

    public static Session session(long id, int charge, int capacity, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory, String fileName,
                                  int width, int height, int size, ImageType imageType, LocalDate startDate,
                                  LocalDate endDate) {
        return paidSession(id, new Charge(charge), new Capacity(capacity), sessionStatus, enrollmentsFactory,
                toImage(fileName, width, height, size, imageType), toSessionPeriod(startDate, endDate));
    }

    public static Session paidSession(long id, Charge charge, Capacity capacity, SessionStatus sessionStatus,
                                      EnrollmentsFactory enrollmentsFactory,
                                      CoverImage coverImage, SessionPeriod sessionPeriod) {
        return new PaidSession(id, charge, capacity, sessionStatus, enrollmentsFactory, coverImage, sessionPeriod);
    }

    public static Session session(long id, Charge charge, SessionStatus sessionStatus,
                                  EnrollmentsFactory enrollmentsFactory,
                                  CoverImage coverImage,
                                  SessionPeriod sessionPeriod) {
        return new Session(id, charge, sessionStatus, enrollmentsFactory, coverImage, sessionPeriod);
    }

    public static Session session(Long id, Charge charge, Enrollments enrollments, CoverImage coverImage,
                                  SessionPeriod sessionPeriod) {
        return new Session(id, charge, enrollments, coverImage, sessionPeriod);
    }
}
