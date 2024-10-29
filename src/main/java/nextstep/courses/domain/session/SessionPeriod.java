package nextstep.courses.domain.session;

import java.time.LocalDate;

public class SessionPeriod {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public SessionPeriod(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("강의 시작일은 종료일보다 빠를 수 없습니다.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }
}
