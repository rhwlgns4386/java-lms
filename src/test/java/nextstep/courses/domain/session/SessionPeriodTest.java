package nextstep.courses.domain.session;

import nextstep.qna.SessionException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionPeriodTest {

    @Test
    void 시작일이_종료일이전이면_예외() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 27, 23, 59);

        assertThatThrownBy(
                () -> new SessionPeriod(startDate, endDate)
        ).isInstanceOf(SessionException.class);
    }

    @Test
    void 시작일과_종료일_범위밖이면_예외() {

        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 30, 23, 59);
        LocalDateTime targetDate = LocalDateTime.of(2024, 10, 29, 23, 59);

        SessionPeriod sessionPeriod = new SessionPeriod(startDate, endDate);

        assertThatThrownBy(
                () -> sessionPeriod.registration(targetDate)
        ).isInstanceOf(SessionException.class);
    }
}
