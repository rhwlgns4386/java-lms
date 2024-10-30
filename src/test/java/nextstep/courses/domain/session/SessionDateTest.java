package nextstep.courses.domain.session;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionDateTest {

    @Test
    void 날짜_저장_성공_테스트() {
        SessionDate sessionDate = new SessionDate(LocalDateTime.of(2024,10,30,10,30), LocalDateTime.of(2024,10,30,10,50));

        assertThat(sessionDate).isEqualTo(new SessionDate(LocalDateTime.of(2024,10,30,10,30), LocalDateTime.of(2024,10,30,10,50)));
    }


    @Test
    void 날짜_저장_실패_테스트() {
        assertThatThrownBy(() -> new SessionDate(LocalDateTime.now(), LocalDateTime.now().minusDays(3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("시작일이 종료일보다 빠를 수 없습니다.");
    }

}
