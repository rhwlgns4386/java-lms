package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;
import nextstep.qna.SessionPayException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionPayTest {

    @Test
    void 강의결제금액이_맞지_않으면_예외() {
        SessionPay sessionPay = new SessionPay(2000L, SessionPayType.PAID);
        Payment payment = new Payment("1", 1L, 1L, 3000L);

        assertThatThrownBy(
                ()->sessionPay.validatePay(payment)
        ).isInstanceOf(SessionPayException.class);

    }
}
