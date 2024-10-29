package nextstep.courses.domain;

import nextstep.courses.Exception.NotAllowedDateException;

import java.time.LocalDateTime;

public class SessionDate {
    private static final String NOT_ALLOWED_DATE_MESSAGE = "시작일은 종료일보다 늦을 수 없습니다.";
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public SessionDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new NotAllowedDateException(NOT_ALLOWED_DATE_MESSAGE);
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
