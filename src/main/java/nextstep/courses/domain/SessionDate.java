package nextstep.courses.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessionDate {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public SessionDate(String startDate, String endDate) {
        this(LocalDate.parse(startDate).atStartOfDay(), LocalDate.parse(endDate).atStartOfDay());
    }

    public SessionDate(LocalDateTime startDate, LocalDateTime endDate) {
        validateSessionDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateSessionDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalStateException("강의의 종료날짜는 시작날짜보다 이전일 수 없습니다.");
        }
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
