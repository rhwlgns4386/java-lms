package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionPeriod {
    private LocalDateTime applyStartDate;

    private LocalDateTime applyEndDate;

    public SessionPeriod(LocalDateTime applyStartDate, LocalDateTime applyEndDate) {
        if(applyEndDate.isBefore(applyStartDate)){
            throw new IllegalArgumentException("종료일은 시작일시 보다 이전 일 수 없습니다.");
        }
        this.applyStartDate = applyEndDate;
        this.applyEndDate = applyEndDate;
    }

}

