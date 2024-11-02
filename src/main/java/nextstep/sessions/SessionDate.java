package nextstep.sessions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessionDate {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public SessionDate(LocalDateTime startDate, LocalDateTime endDate) {
        validateSessionDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static SessionDate of(String startDate, String endDate) {
        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atStartOfDay();
        return new SessionDate(startDateTime, endDateTime);
    }

    public static SessionDate of(LocalDateTime startDate, LocalDateTime endDate) {
        return new SessionDate(startDate, endDate);
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
