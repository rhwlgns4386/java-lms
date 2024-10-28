package nextstep.courses.domain;

import java.time.LocalDate;

public class Course2Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Course2Period(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("강의 시작일은 종료일보다 빠를 수 없습니다.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }
}
