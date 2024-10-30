package nextstep.courses.domain.session;

import java.time.LocalDate;

public class SessionDate {

    private final LocalDate start;
    private final LocalDate end;

    public SessionDate(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public boolean isInclude(LocalDate date) {
        return (date.isEqual(start) || date.isEqual(end) || (date.isAfter(start) && date.isBefore(end)));
    }
}
