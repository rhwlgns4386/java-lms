package nextstep.payments.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PaymentTest {

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치하지 않을 때 수강 신청 불가하다.")
    void throw_IllegalArgumentException_when_paidAmount_is_not_matched_with_sessionFee() {
        Payment payment = new Payment("PAYMENT_ID", 0L, 0L, 10000L);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> payment.validateSessionFee(100001))
                .withMessage("고객이 결제한 금액과 수강료가 일치하지 않습니다.");
    }
}
