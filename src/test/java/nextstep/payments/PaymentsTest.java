package nextstep.payments;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static nextstep.courses.domain.PaidSessionTest.ps1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaymentsTest {

    @Test
    @DisplayName("유저가 결제하지 않았을 경우, 예외를 발생시킨다.")
    void 결제안함() {
        Payment payment = new Payment("1", 1L, 2L, 3000L);
        Payments payments = new Payments(List.of(payment));
        assertThatThrownBy(() -> payments.getPayments(ps1, NsUserTest.JAVAJIGI))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("유저가 결제한 강의의 결제 정보를 가져온다.")
    void 결제정보() {
        Payment payment = new Payment("1", 1L, 1L, 3000L);
        Payments payments = new Payments(List.of(payment));
        assertThat(payments.getPayments(ps1, NsUserTest.JAVAJIGI)).isEqualTo(payment);
    }

    @Test
    @DisplayName("결제를 하면 목록에 추가한다.")
    void 결제추가() {
        Payments payments = new Payments(new ArrayList<>());
        payments.payment(new Payment());
        assertThat(payments.getSize()).isEqualTo(1);
    }
}
