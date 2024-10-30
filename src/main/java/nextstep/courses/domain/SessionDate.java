package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionDate {
    private LocalDateTime start;
    private LocalDateTime end;

    public SessionDate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.start = start;
        this.end = end;
    }
}
