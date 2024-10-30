package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionDateTest {

    @DisplayName("시작일이 끝날보다 빠르면 생성된다")
    @Test
    void create() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        SessionDate sessionDate = new SessionDate(start, end);

        assertThat(sessionDate).isEqualTo(new SessionDate(start, end));
    }

    @DisplayName("시작일이 끝날보다 늦을수 없다")
    @Test
    void verifyDateRange() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 12);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        assertThatThrownBy(() -> new SessionDate(start, end))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
