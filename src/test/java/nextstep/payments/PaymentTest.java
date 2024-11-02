package nextstep.payments;

import nextstep.fixture.PaymentCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest {

    @Test
    @DisplayName("지불한 금액이 다르면 false 반환")
    void 지불한금액_다름() {
        assertThat(PaymentCreator.pay(3000L).isPaid(2999L)).isFalse();
    }

    @Test
    @DisplayName("지불한 금액이 맞으면 true 반환")
    void 지불한금액_확인() {
        assertThat(PaymentCreator.pay(3000L).isPaid(3000L)).isTrue();
    }
}
