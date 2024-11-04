package nextstep.courses.domain.session.enrollment;

import nextstep.courses.CannotApplyException;
import nextstep.payments.domain.Payment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PriceTest {
    public static Price createFreePrice() {
        return new Price(0L, PayType.FREE);
    }

    public static Price createPayPrice(long price) {
        return new Price(price, PayType.PAY);
    }

    @Test
    @DisplayName("결제 금액과 수강료가 일치하지 않는 경우 CannotApplyException이 발생한다.")
    void isValid_결제_금액과_수강료가_일치하지_않는_경우() {
        Price price = createPayPrice(800_000L);

        Payment payment = new Payment("1", 1L, 1L, 500_000L);

        Assertions.assertThatThrownBy(() -> {
            price.isValid(payment);
        }).isInstanceOf(CannotApplyException.class);
    }
}
