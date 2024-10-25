package nextstep.payments;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.PaidSessionTest.ps1;

public class PaymentTest {

    @Test
    @DisplayName("강의와 유저 id가 같으면 true를 반환한다.")
    void 강의_유저_매칭() {
        Payment payment = new Payment("1", 1L, 1L, 3000L);
        Assertions.assertThat(payment.isPaidUser(ps1, NsUserTest.JAVAJIGI)).isTrue();
    }
}
