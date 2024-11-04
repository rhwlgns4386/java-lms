package nextstep.courses.domain;

import java.time.LocalDate;

public class SessionDate {
    private final String ERROR_START_DAY_MESSAGE = "종료일이 시작일보다 과거일수 없습니다.";
    private final LocalDate sessionStart;
    private final LocalDate sessionEnd;

    public SessionDate(LocalDate sessionStart, LocalDate sessionEnd) {
        if (sessionStart.isAfter(sessionEnd)) {
            throw new IllegalArgumentException(ERROR_START_DAY_MESSAGE);
        }
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
    }

    public LocalDate getSessionStart() {
        return sessionStart;
    }

    public LocalDate getSessionEnd() {
        return sessionEnd;
    }
}
