package nextstep.courses.infrastructure;

import java.time.LocalDate;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.EnrollmentsInfo;
import nextstep.courses.domain.LimitEnrollmentsInfo;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionStatus;

class SessionHelper {
    private final Long id;
    private final Charge charge;
    private final EnrollmentsInfo enrollmentsInfo;
    private final SessionPeriod sessionPeriod;

    public SessionHelper(Session session) {
        this.id = session.id();
        this.charge = session.charge();
        this.enrollmentsInfo = session.enrollmentsInfo();
        this.sessionPeriod = session.sessionPeriod();
    }

    int getCharge() {
        return charge.toInt();
    }

    Integer getCapacity() {
        if (enrollmentsInfo instanceof LimitEnrollmentsInfo) {
            LimitEnrollmentsInfo limitedEnrollments = (LimitEnrollmentsInfo) enrollmentsInfo;
            return limitedEnrollments.capacity();
        }
        return null;
    }

    String getSessionStatus() {
        SessionStatus sessionStatus = enrollmentsInfo.sessionStatus();
        return sessionStatus.name();
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
