package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionAmountTest {
    @Test
    void 수강료_음수일떄_예외발생() {
        assertThrows(IllegalArgumentException.class, () -> new SessionAmount(-1L));
    }
}