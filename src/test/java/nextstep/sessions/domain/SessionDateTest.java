package nextstep.sessions.domain;

import nextstep.sessions.SessionDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionDateTest {

    @Test
    public void 세션_날짜_정상_생성_테스트() {
        SessionDate sessionDate = new SessionDate("2021-08-01", "2021-08-31");

        assertThat(sessionDate.getStartDate()).isEqualTo(LocalDateTime.of(2021, 8, 1, 0, 0));
        assertThat(sessionDate.getEndDate()).isEqualTo(LocalDateTime.of(2021, 8, 31, 0, 0));
    }

    @Test
    public void 종료날짜가_시작날짜보다_이전이면_예외_발생() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            new SessionDate("2021-08-31", "2021-08-01");
        });
        assertThat(exception.getMessage()).isEqualTo("강의의 종료날짜는 시작날짜보다 이전일 수 없습니다.");
    }

    @Test
    public void 시작날짜와_종료날짜가_같은_경우_정상_생성_테스트() {
        SessionDate sessionDate = new SessionDate("2021-08-01", "2021-08-01");

        assertThat(sessionDate.getStartDate()).isEqualTo(LocalDateTime.of(2021, 8, 1, 0, 0));
        assertThat(sessionDate.getEndDate()).isEqualTo(LocalDateTime.of(2021, 8, 1, 0, 0));
    }
}
