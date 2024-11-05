package nextstep.courses.domain.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoneyTest {
    @Test
    @DisplayName("Money 생성")
    void createMoneyTest() {
        long price = 100_000L;

        Money money = new Money(price);

        Assertions.assertThat(money).isNotNull();
    }

    @Test
    @DisplayName("price 유효성 체크")
    void checkMoneyPriceTest() {
        long price = 0L;

        Assertions.assertThatThrownBy(() -> new Money(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
