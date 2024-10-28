package nextstep.payments.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void 가격을_검증한다() {
        Payment payment = createPayment(1L, 1L, 3000L);
        boolean result = payment.isSamePrice(3000L);

        assertThat(result).isTrue();
    }

    @Test
    void 유저를_검증한다() {
        Payment payment = createPayment(1L, 1L, 3000L);
        boolean result = payment.verifyUser(1L);

        assertThat(result).isTrue();
    }

    @Test
    void 강의정보를_검증한다() {
        Payment payment = createPayment(1L, 1L, 3000L);
        boolean result = payment.verifySession(1L);

        assertThat(result).isTrue();
    }

    private Payment createPayment(Long sessionId, Long studentId, Long price) {
        return new Payment("id", sessionId, studentId, price);
    }
}
