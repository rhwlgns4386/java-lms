package nextstep.courses.factory;

import java.time.LocalDate;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;

public class SessionFactory {

    public static Session freeSession(SessionStatus sessionStatus, String fileName, int width, int height, int size,
                                      ImageType imageType, LocalDate startDate, LocalDate endDate) {

        return new Session(Charge.ZERO, new Enrollments(sessionStatus),
                toImage(fileName, width, height, size, imageType), toSessionPeriod(startDate, endDate));
    }

    private static CoverImage toImage(String fileName, int width, int height, int size, ImageType imageType) {
        return new CoverImage(fileName, width, height, size, imageType);
    }

    private static SessionPeriod toSessionPeriod(LocalDate startDate, LocalDate endDate) {
        return new SessionPeriod(startDate, endDate);
    }
}
