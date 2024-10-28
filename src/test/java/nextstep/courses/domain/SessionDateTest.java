package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SessionDateTest {
    public static final SessionDate sd1 = new SessionDate(LocalDateTime.of(2024, 10, 24, 0, 0),
            LocalDateTime.of(2024, 10, 25, 0, 0));

    @Test
    @DisplayName("시작일이 종료일보다 크면 예외를 발생시킨다.")
    void 시작일_종료일_예외() {
        Assertions.assertThatThrownBy(() -> new SessionDate(LocalDateTime.of(2024, 2, 1, 0, 0),
                        LocalDateTime.of(2024, 1, 1, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
