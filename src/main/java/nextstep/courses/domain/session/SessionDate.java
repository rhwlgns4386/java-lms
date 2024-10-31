package nextstep.courses.domain.session;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDate {

    private static final String DATE_ERROR = "시작일이 종료일보다 빠를 수 없습니다.";

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public SessionDate(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        isValid(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void isValid(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException(DATE_ERROR);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionDate that = (SessionDate) o;
        return Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime);
    }

}
