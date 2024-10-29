package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;
import nextstep.qna.SessionPayException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionPaysTest {

    @Test
    void 결제_금액_일치하지_않으면_예외() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 1000L);
        SessionPays sessionPays = new SessionPays(2000L);

        assertThatThrownBy(
                () -> sessionPays.addPayments(payment)
        ).isInstanceOf(SessionPayException.class);
    }
}