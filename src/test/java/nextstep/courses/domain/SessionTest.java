package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

class SessionTest {

    @DisplayName("강의 상태를 가지고 무료 강의를 생성한다")
    @Test
    void createFreeSession() {
        Session freeSession = SessionTestFixture.createFreeSession(1L, SessionStatus.PREPARE, RegisterStatus.REGISTER);
        assertThat(freeSession).isEqualTo(SessionTestFixture.createFreeSession(1L, SessionStatus.PREPARE, RegisterStatus.REGISTER));
    }

    @DisplayName("가격과 강의 상태를 가지고 유료 강의를 생성한다")
    @Test
    void createPaidSession() {
        Session paidSession = SessionTestFixture.createPaidSession(1L, 3000L, 3, SessionStatus.PREPARE, RegisterStatus.REGISTER);
        assertThat(paidSession).isEqualTo(SessionTestFixture.createPaidSession(1L, 3000L, 3, SessionStatus.PREPARE, RegisterStatus.REGISTER));
    }

    @DisplayName("강의가 닫히거나 모집중이 아닌 상태라면 학생을 등록하지 못한다")
    @Test
    void addStudent() {
        Session freeSession = SessionTestFixture.createFreeSession(1L, SessionStatus.CLOSE, RegisterStatus.REGISTER);
        Session notRegisterSession = SessionTestFixture.createFreeSession(1L, SessionStatus.PREPARE, RegisterStatus.NOTREGISTER);

        assertThatThrownBy(() -> freeSession.register(NsUserTest.GYEONGJAE))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("결제금액이 유료강의 수강료와 같으면 수강신청된다")
    @Test
    void register() {
        Payment payment = new Payment("id", 1L, 1L, 3000L);
        Session paidSession = SessionTestFixture.createPaidSession(1L, 3000L, 3, SessionStatus.PREPARE, RegisterStatus.REGISTER);

        paidSession.register(payment, NsUserTest.JAVAJIGI);

        assertThat(paidSession.studentSize()).isEqualTo(1);
    }

    @DisplayName("모집중이 아니면 등록하지 못한다")
    @Test
    void register_throw_exception() {
        Payment payment = new Payment("id", 1L, 1L, 3000L);
        Session paidSession = SessionTestFixture.createPaidSession(1L, 3000L, 3, SessionStatus.PREPARE, RegisterStatus.NOTREGISTER);

        assertThatThrownBy(() -> paidSession.register(payment, NsUserTest.JAVAJIGI))
            .isInstanceOf(IllegalStateException.class);
    }
}
