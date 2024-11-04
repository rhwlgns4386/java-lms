package nextstep.courses.domain.session.enrollment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PayTypeTest {

    @Test
    @DisplayName("무료 강의인 경우 가격은 0원이어야 한다.")
    void isValid_무료강의() {
        PayType free = PayType.FREE;

        Assertions.assertThatThrownBy(() -> {
            free.isValid(800_000L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유료 강의인 경우 가격은 0원 이상이어야 한다.")
    void isValid_유료강의() {
        PayType pay = PayType.PAY;

        Assertions.assertThatThrownBy(() -> {
            pay.isValid(0L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
