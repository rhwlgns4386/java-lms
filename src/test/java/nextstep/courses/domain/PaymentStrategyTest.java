package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentStrategyTest {

    @Test
    void create() {
         PaymentStrategy expected = new PaidPaymentStrategy(1000, 10);

         assertThat(expected).isEqualTo(new PaidPaymentStrategy(1000, 10));
    }

    @Test
    void 유료_수강신청() {
        PaymentStrategy paymentStrategy = new PaidPaymentStrategy(1000, 10);

        boolean expected = paymentStrategy.payable(new Payment("1L", 1L, 1L, 1000L));

        assertThat(expected).isEqualTo(true);
    }

    @Test
    void 무료_수강신청() {
        PaymentStrategy paymentStrategy = new FreePaymentStrategy();

        boolean expected = paymentStrategy.payable(new Payment());

        assertThat(expected).isEqualTo(true);
    }
}
