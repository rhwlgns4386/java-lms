package nextstep.courses.domain.session;

import nextstep.qna.SessionPeriodException;

import java.time.LocalDateTime;

public class SessionPeriod {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public SessionPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validate(startDate,endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDateTime startDate, LocalDateTime endDate) {
        if(endDate.isBefore(startDate)){
            throw new SessionPeriodException("종료일이 시작일 이전입니다. 확인해 주세요");
        }
    }

    public void registration(LocalDateTime targetDate){
        if(targetDate.isAfter(startDate)){
            throw new SessionPeriodException("시작일 이후에는 수강신청 할 수 없습니다");
        }
    }

}
