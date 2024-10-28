package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class SessionDateTest {

    @Test
    void 생성() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        SessionDate sessionDate = new SessionDate(start, end);

        assertThat(sessionDate).isEqualTo(new SessionDate(start, end));
    }

    @Test
    void 시작일이_끝날보다_늦을수_없다() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 12);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        assertThatThrownBy(() -> new SessionDate(start, end))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
