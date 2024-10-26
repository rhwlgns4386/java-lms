package nextstep.courses.domain.session;

import nextstep.courses.dto.SessionReservation;
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

        SessionReservation sessionReservation = session.reserveSession();

        assertThat(sessionReservation.getSessionFee()).isEqualTo(0);
    }

    @Test
    void reserve_paid_session() {
        Session session = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        SessionReservation sessionReservation = session.reserveSession();

        assertThat(sessionReservation.getSessionFee()).isEqualTo(10000);
    }

    @Test
    void throw_exception_if_exceed_max_enrollment() {
        Session session = SessionBuilderTest.paidSessionBuilder()
                .enrollment(0)
                .sessionState(SessionState.OPEN)
                .build();

        assertThatIllegalStateException().isThrownBy(session::reserveSession);
    }

    @Test
    void throw_exception_if_session_is_not_open() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder().build();
        Session freeSession = SessionBuilderTest.freeSessionBuilder().build();

        assertThatIllegalStateException().isThrownBy(paidSession::reserveSession);
        assertThatIllegalStateException().isThrownBy(freeSession::reserveSession);
    }

    @Test
    void complete_register_free_session() {
        Session freeSession = SessionBuilderTest.freeSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        Payment payment = new Payment("free", null, NsUserTest.JAVAJIGI.getId(), 0L);

        assertThat(freeSession.completeRegister(payment)).isTrue();
    }

    @Test
    void complete_register_paid_session() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.OPEN)
                .build();

        Payment payment = new Payment("paid", null, NsUserTest.JAVAJIGI.getId(), 10000L);

        assertThat(paidSession.completeRegister(payment)).isTrue();
    }
}
