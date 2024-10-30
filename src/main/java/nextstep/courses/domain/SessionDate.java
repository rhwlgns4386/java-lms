package nextstep.courses.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDate {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public SessionDate(Timestamp sessionStartAt, Timestamp sessionEndAt) {
        this(sessionStartAt.toLocalDateTime(), sessionEndAt.toLocalDateTime());
    }

    public SessionDate(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt)) {
            throw new IllegalArgumentException("종료일은 시작일보다 작을 수 없습니다.");
        }
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SessionDate that = (SessionDate) object;
        return Objects.equals(getStartAt(), that.getStartAt()) && Objects.equals(getEndAt(), that.getEndAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartAt(), getEndAt());
    }
}
