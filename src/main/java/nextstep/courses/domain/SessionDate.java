package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionDate {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public SessionDate(LocalDateTime startAt, LocalDateTime endAt) {
        validationDate(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private static void validationDate(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt)) {
            throw new IllegalArgumentException("종료일은 시작일보다 작을 수 없습니다.");
        }
    }
}
