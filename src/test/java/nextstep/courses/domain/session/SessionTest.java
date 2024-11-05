package nextstep.courses.domain.session;

import nextstep.courses.domain.enrollment.Student;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SelectionType;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
                .recruitState(RecruitState.RECRUIT)
                .build();

        assertThatIllegalStateException().isThrownBy(() -> session.apply(new Student(NsUserTest.JAVAJIGI), paidPayment))
                .withMessageContaining("인원이 초과");
    }

    @Test
    void throw_exception_if_register_not_applied_student() {
        Session session = SessionBuilderTest.paidSessionBuilder()
                .maxEnrollment(1)
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> session.register(NsUserTest.JAVAJIGI))
                .withMessageContaining("수강 신청한 사람이");
    }

    @Test
    void throw_exception_if_session_is_not_recruit() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder().sessionState(SessionState.PREPARING).build();
        Session freeSession = SessionBuilderTest.freeSessionBuilder().sessionState(SessionState.END).build();

        assertThatIllegalStateException().isThrownBy(() -> paidSession.apply(new Student(NsUserTest.JAVAJIGI), paidPayment))
                .withMessageContaining("모집 중");
        assertThatIllegalStateException().isThrownBy(() -> freeSession.apply(new Student(NsUserTest.JAVAJIGI), freePayment))
                .withMessageContaining("모집 중");;
    }

    @Test
    void register_free_session() {
        Session freeSession = SessionBuilderTest.freeSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();

        freeSession.apply(new Student(NsUserTest.JAVAJIGI), freePayment);
        assertThat(freeSession.register(NsUserTest.JAVAJIGI).isRegistered()).isTrue();
    }

    @Test
    void register_paid_session() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();

        paidSession.apply(new Student(NsUserTest.JAVAJIGI), paidPayment);
        assertThat(paidSession.register(NsUserTest.JAVAJIGI).isRegistered()).isTrue();
    }

    @Test
    void throw_exception_if_apply_with_invalid_session_id_payment() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();
        assertThatIllegalStateException().isThrownBy(() -> paidSession.apply(
                        new Student(NsUserTest.JAVAJIGI),
                        new Payment("", 1L, NsUserTest.JAVAJIGI.getId(), 10000L)))
                .withMessageContaining("결제 내역(금액 등)");
    }

    @Test
    void throw_exception_if_apply_with_invalid_session_fee_payment() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();

        assertThatIllegalStateException().isThrownBy(() -> paidSession.apply(
                        new Student(NsUserTest.JAVAJIGI),
                        new Payment("", -1L, NsUserTest.JAVAJIGI.getId(), 100L)))
                .withMessageContaining("결제 내역(금액 등)");
    }

    @Test
    void reject_apply() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .build();
        paidSession.apply(new Student(NsUserTest.JAVAJIGI), paidPayment);

        assertThat(paidSession.reject(NsUserTest.JAVAJIGI).isRegistered()).isFalse();
    }

    @Test
    void register_select_student() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .selectionType(SelectionType.SELECTION)
                .build();
        paidSession.apply(new Student(NsUserTest.JAVAJIGI), paidPayment);
        paidSession.select(NsUserTest.JAVAJIGI);

        assertThat(paidSession.register(NsUserTest.JAVAJIGI).isRegistered()).isTrue();
    }

    @Test
    void throw_exception_if_register_non_select_student() {
        Session paidSession = SessionBuilderTest.paidSessionBuilder()
                .sessionState(SessionState.PROGRESS)
                .recruitState(RecruitState.RECRUIT)
                .selectionType(SelectionType.SELECTION)
                .build();
        paidSession.apply(new Student(NsUserTest.JAVAJIGI), paidPayment);

        assertThatIllegalStateException().isThrownBy(() -> paidSession.register(NsUserTest.JAVAJIGI));
    }
}
