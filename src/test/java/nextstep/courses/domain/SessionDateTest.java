package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SessionDateTest {
    @Test
    @DisplayName("SessionDate 생성")
    void createSessionDateTest() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        SessionDate sessionDate = new SessionDate(start, end);

        Assertions.assertThat(sessionDate).isNotNull();

    }

    @Test
    @DisplayName("SessionDate의 startDateTime endDateTime보다 나중일 수 없다")
    void startDateTimeIsAfterEndDateTimeTest() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 12);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        Assertions.assertThatThrownBy(() -> new SessionDate(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
