package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SessionDateTest {
    @Test
    void 수강기간_생성() {
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        SessionDate sessionDate = new SessionDate(startDate, endDate);
        assertThat(sessionDate).isNotNull();
    }

    @Test
    void 시작일_검증() {
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 9, 30);
        assertThatThrownBy(() -> new SessionDate(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
