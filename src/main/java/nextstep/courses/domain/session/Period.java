package nextstep.courses.domain.session;

import java.time.LocalDate;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("강의 시작일은 종료일보다 빠를 수 없습니다.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
