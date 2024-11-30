package nextstep.courses.factory;

import java.time.LocalDate;
import nextstep.courses.domain.SessionPeriod;

public class SessionPeriodConverter {
    static SessionPeriod toSessionPeriod(LocalDate startDate, LocalDate endDate) {
        return new SessionPeriod(startDate, endDate);
    }
}
