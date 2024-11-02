package nextstep.courses.domain.session;

import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class SessionTest {

    private static final Payment paidPayment =
            new Payment("test", -1L, NsUserTest.JAVAJIGI.getId(), 10000L);
    private static final Payment freePayment =
            new Payment("test", -1L, NsUserTest.JAVAJIGI.getId(), 0L);

    @Test
    void throw_exception_if_exceed_max_enrollment() {
        Session session = SessionBuilderTest.paidSessionBuilder()
                .maxEnrollment(0)
                .sessionState(SessionState.PROGRESS)
                .build();

        assertThatIllegalStateException().isThrownBy(() -> session.register(paidPayment));
    }

    @Test
    void throw_exception_if_session_is_not_open() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder().sessionState(SessionState.PREPARING).build();
        Session freeSession = SessionBuilderTest.freeSessionBuilder().sessionState(SessionState.END).build();

        assertThatIllegalStateException().isThrownBy(() -> paidSession.register(paidPayment));
        assertThatIllegalStateException().isThrownBy(() -> freeSession.register(freePayment));
    }

    @Test
    void register_free_session() {
        Session freeSession = SessionBuilderTest.freeSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();

        assertThat(freeSession.register(freePayment)).isTrue();
    }

    @Test
    void register_paid_session() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();

        assertThat(paidSession.register(paidPayment)).isTrue();
    }

    @Test
    void throw_exception_if_register_with_invalid_session_id_payment() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .build();

        assertThatIllegalStateException().isThrownBy(() -> paidSession.register(
                new Payment("", 1L, NsUserTest.JAVAJIGI.getId(), 10000L)));
    }

    @Test
    void throw_exception_if_register_with_invalid_session_fee_payment() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .build();

        assertThatIllegalStateException().isThrownBy(() -> paidSession.register(
                new Payment("", -1L, NsUserTest.JAVAJIGI.getId(), 100L)));
    }
}
