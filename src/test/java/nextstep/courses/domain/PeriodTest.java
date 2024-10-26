package nextstep.courses.domain;

import nextstep.courses.domain.session.Period;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PeriodTest {
    public static final Period PERIOD = new Period(
            LocalDate.of(2021, 1, 1)
            , LocalDate.of(2021, 1, 2));

    @Test
    void 기간생성성공() {
        LocalDate startDate = LocalDate.of(2024, 10, 26);
        LocalDate endDate = LocalDate.of(2024, 10, 28);
        Assertions.assertThatNoException().isThrownBy(() -> new Period(startDate, endDate));
    }

    @Test
    void 기간생성실패() {
        LocalDate startDate = LocalDate.of(2024, 10, 26);
        LocalDate endDate = LocalDate.of(2024, 10, 24);
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> new Period(startDate, endDate));
    }
}
