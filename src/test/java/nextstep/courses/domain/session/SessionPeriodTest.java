package nextstep.courses.domain.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class SessionPeriodTest {

    @Test
    @DisplayName("강의 종료일이 강의 시작일보다 이전인 경우 IllegalArgumentException이 발생한다.")
    void create_강의종료일이_강의시작일보다_이전일_경우() {
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1L);
        LocalDateTime endDateTime = LocalDateTime.now();

        Assertions.assertThatThrownBy(() -> {
            SessionPeriod sessionPeriod = new SessionPeriod(startDateTime, endDateTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
