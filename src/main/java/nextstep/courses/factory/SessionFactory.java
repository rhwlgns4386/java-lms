package nextstep.courses.factory;

import static nextstep.courses.factory.CoverImageConverter.toImage;
import static nextstep.courses.factory.SessionPeriodConverter.toSessionPeriod;

import java.time.LocalDate;
import nextstep.courses.domain.Capacity;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.LimitedEnrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;

public class SessionFactory {

    public static Session freeSession(SessionStatus sessionStatus, String fileName, int width, int height, int size,
                                      ImageType imageType, LocalDate startDate, LocalDate endDate) {

        return freeSession(sessionStatus, toImage(fileName, width, height, size, imageType),
                toSessionPeriod(startDate, endDate));
    }

    public static Session freeSession(SessionStatus sessionStatus, CoverImage coverImage, SessionPeriod sessionPeriod) {

        return new Session(Charge.ZERO, new Enrollments(sessionStatus), coverImage, sessionPeriod);
    }

    public static Session paidSession(int charge, int capacity, SessionStatus sessionStatus, String fileName, int width,
                                      int height, int size, ImageType imageType, LocalDate startDate,
                                      LocalDate endDate) {

        return paidSession(new Charge(charge), new Capacity(capacity), sessionStatus,
                toImage(fileName, width, height, size, imageType), toSessionPeriod(startDate, endDate));
    }


    public static Session paidSession(Charge charge, Capacity capacity, SessionStatus sessionStatus,
                                      CoverImage coverImage,
                                      SessionPeriod sessionPeriod) {

        return new Session(charge, new LimitedEnrollments(capacity, sessionStatus), coverImage, sessionPeriod);
    }
}
