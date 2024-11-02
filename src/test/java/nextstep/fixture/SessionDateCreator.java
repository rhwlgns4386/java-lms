package nextstep.fixture;

import nextstep.courses.domain.SessionDate;

import java.time.LocalDateTime;

public class SessionDateCreator {

    public static SessionDate standard() {
        return new SessionDate(LocalDateTime.of(2024, 10, 24, 0, 0),
                LocalDateTime.of(2024, 10, 25, 0, 0));
    }

    public static SessionDate date(LocalDateTime start, LocalDateTime end) {
        return new SessionDate(start, end);
    }
}
