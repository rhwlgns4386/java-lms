package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

class PaidSessionTest {

    @Test
    void 생성() {
        Session paidSession = SessionTest.createPaidSession(3000L, SessionStatus.PREPARE);
        assertThat(paidSession).isEqualTo(SessionTest.createPaidSession(3000L, SessionStatus.PREPARE));
    }

    @Test
    void 결제금액이_수강료와_같으면_수강신청된다() {
        Payment payment = new Payment("id", 1L, 1L, 3000L);
        PaidSession paidSession = SessionTest.createPaidSession(3000L, SessionStatus.RESITER);

        paidSession.register(payment, NsUserTest.JAVAJIGI);

        assertThat(paidSession.studentSize()).isEqualTo(1);
    }

    @Test
    void 모집중이_아니면_등록하지_못한다() {
        Payment payment = new Payment("id", 1L, 1L, 3000L);
        PaidSession paidSession = SessionTest.createPaidSession(3000L, SessionStatus.PREPARE);

        assertThatThrownBy(() -> paidSession.register(payment, NsUserTest.JAVAJIGI))
            .isInstanceOf(IllegalStateException.class);
    }
}
