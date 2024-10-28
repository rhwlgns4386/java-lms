package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {
    @DisplayName("0보다 작은 금액은 예외로 처리한다.")
    @Test
    void invalidMoney() {
        assertThatThrownBy(() -> new Money(-1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
