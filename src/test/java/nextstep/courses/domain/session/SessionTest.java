package nextstep.courses.domain.session;

import nextstep.courses.dto.SessionPaymentInfo;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class SessionTest {

    @Test
    void reserve_free_session() {
        Session session = SessionBuilderTest.freeSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        SessionPaymentInfo sessionPaymentInfo = session.tryRegisterForSession();

        assertThat(sessionPaymentInfo.getSessionFee()).isEqualTo(0);
    }

    @Test
    void reserve_paid_session() {
        Session session = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        SessionPaymentInfo sessionPaymentInfo = session.tryRegisterForSession();

        assertThat(sessionPaymentInfo.getSessionFee()).isEqualTo(10000);
    }

    @Test
    void throw_exception_if_exceed_max_enrollment() {
        Session session = SessionBuilderTest.paidSessionBuilder()
                .enrollment(0)
                .sessionState(SessionState.OPEN)
                .build();

        assertThatIllegalStateException().isThrownBy(session::tryRegisterForSession);
    }

    @Test
    void throw_exception_if_session_is_not_open() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder().build();
        Session freeSession = SessionBuilderTest.freeSessionBuilder().build();

        assertThatIllegalStateException().isThrownBy(paidSession::tryRegisterForSession);
        assertThatIllegalStateException().isThrownBy(freeSession::tryRegisterForSession);
    }

    @Test
    void finalize_register_free_session() {
        Session freeSession = SessionBuilderTest.freeSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        Payment payment = new Payment("free", -1L, NsUserTest.JAVAJIGI.getId(), 0L);

        assertThat(freeSession.finalizeSessionRegistration(payment)).isTrue();
    }

    @Test
    void finalize_register_paid_session() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        Payment payment = new Payment("paid", -1L, NsUserTest.JAVAJIGI.getId(), 10000L);

        assertThat(paidSession.finalizeSessionRegistration(payment)).isTrue();
    }

    @Test
    void fail_to_finalize_register_with_invalid_session_id_payment() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        assertThat(paidSession.finalizeSessionRegistration(
                new Payment("", 1L, NsUserTest.JAVAJIGI.getId(), 10000L))).isFalse();
    }

    @Test
    void fail_to_finalize_register_with_invalid_session_fee_payment() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        assertThat(paidSession.finalizeSessionRegistration(
                new Payment("", -1L, NsUserTest.JAVAJIGI.getId(), 100L))).isFalse();
    }
}
