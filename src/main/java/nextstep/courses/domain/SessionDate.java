package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDate {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public SessionDate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SessionDate))
            return false;
        SessionDate that = (SessionDate)o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
