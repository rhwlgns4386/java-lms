package nextstep.courses.infrastructure;

import java.time.LocalDate;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.DefaultEnrollments;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.LimitedEnrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;

class SessionHelper {
    private final Long id;
    private final Charge charge;
    private final DefaultEnrollments enrollments;
    private final CoverImage coverImage;
    private final SessionPeriod sessionPeriod;

    public SessionHelper(Session session) {
        this.id = session.id();
        this.charge = session.charge();
        this.enrollments = session.enrollments();
        this.coverImage = session.coverImage();
        this.sessionPeriod = session.sessionPeriod();
    }

    int getCharge() {
        return charge.toInt();
    }

    Integer getCapacity() {
        if (enrollments instanceof LimitedEnrollments) {
            LimitedEnrollments limitedEnrollments = (LimitedEnrollments) enrollments;
            return limitedEnrollments.capacity();
        }
        return null;
    }

    String getSessionStatus() {
        SessionStatus sessionStatus = enrollments.sessionStatus();
        return sessionStatus.name();
    }

    String getImageFileName() {
        return coverImage.fileName();
    }

    int getImageWidth() {
        return coverImage.width();
    }

    int getImageHeight() {
        return coverImage.height();
    }

    int getImageBytes() {
        return coverImage.bytes();
    }

    String getImageType() {
        ImageType imageType = coverImage.type();
        return imageType.name();
    }

    LocalDate getStartDate() {
        return sessionPeriod.startDate();
    }

    LocalDate getEndDate() {
        return sessionPeriod.endDate();
    }

    long getId() {
        return id;
    }
}
