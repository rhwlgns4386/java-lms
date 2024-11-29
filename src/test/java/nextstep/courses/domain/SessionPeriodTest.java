package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class SessionPeriodTest {

    @Test
    void 종료일은_시작일보다_빠를_수_없다() {
        LocalDate endDate = LocalDate.of(2024, 12, 8);
        LocalDate startDate = LocalDate.of(2024, 12, 9);
        assertThatIllegalArgumentException().isThrownBy(() -> new SessionPeriod(startDate, endDate));
    }

    @Test
    void 시작일이_종료일보다_빠르거다_같다면_정상_생성() {
        LocalDate endDate = LocalDate.of(2024, 12, 9);
        LocalDate startDate = LocalDate.of(2024, 12, 9);
        assertThatCode(() -> new SessionPeriod(startDate, endDate)).doesNotThrowAnyException();
    }
}
