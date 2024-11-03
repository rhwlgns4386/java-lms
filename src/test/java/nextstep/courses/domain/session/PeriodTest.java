package nextstep.courses.domain.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeriodTest {

    @DisplayName("강의 종료일이 시작일 보다 빠르면 예외로 처리한다.")
    @Test
    void create() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2023, 2, 19);

        assertThatThrownBy(() -> new Period(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
