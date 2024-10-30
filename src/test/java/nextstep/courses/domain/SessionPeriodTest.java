package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionPeriodTest {

    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate TOMORROW = LocalDate.now().plusDays(1L);

    @Test
    void 시작일_종료일_생성() {
        assertEquals(new SessionPeriod(TODAY, TOMORROW), new SessionPeriod(TODAY, TOMORROW));
    }

    @Test
    void 종료일이_시작일보다_빠른_경우_예외_발생() {
        assertThrows(IllegalArgumentException.class, () -> new SessionPeriod(TOMORROW, TODAY));
    }
}