package nextstep.courses.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessionDate {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    public SessionDate(String startDate, String endDate) {
        this.startDate = LocalDate.parse(startDate).atStartOfDay();
        this.endDate = LocalDate.parse(endDate).atStartOfDay();
    }
}
