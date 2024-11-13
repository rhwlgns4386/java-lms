package nextstep.enrollment.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.Session;
import nextstep.session.domain.fixture.FixtureEnrollmentFactory;
import nextstep.session.domain.fixture.FixturePaymentFactory;
import nextstep.session.domain.fixture.FixtureSessionFactory;
import nextstep.users.domain.NsUserTest;

class EnrollmentTest {

    @Test
    @DisplayName("수강신청한 사람 중 선발되지 않은 사람은 수강을 취소할 수 있다.")
    void cancelTest() {
        Session session = getRecruitingFreeSession();
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);
        assertDoesNotThrow(() -> enrollment.cancel());
    }

    @Test
    @DisplayName("수강신청한 사람 중 승인된 사람이 취소할 수 경우 예외가 발생한다.")
    void throwExceptionWhen() {
        Session session = getRecruitingFreeSession();
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);
        enrollment.approve();

        Assertions.assertThatThrownBy(() -> enrollment.cancel())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("수강신청 승인되어 취소할 수 없습니다.");
    }

    @Test
    @DisplayName("모집중인 강의가 아니라면 무료 강의를 생성할 때 예외가 발생한다.")
    void throwExceptionWhenFreeEnrollmentBySessionIsNotRecruiting() {
        Session session = FixtureSessionFactory.createFreeSession(1L);

        Assertions.assertThatThrownBy(() -> Enrollment.free(1L, session, NsUserTest.JAVAJIGI))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("모집중인 강의가 아닙니다.");
    }

    @Test
    @DisplayName("모집중인 강의가 아니라면 유료 강의를 생성할 때 예외가 발생한다.")
    void throwExceptionWhenPaidEnrollmentBySessionIsNotRecruiting() {
        Session session = FixtureSessionFactory.createFreeSession(1L);

        Assertions.assertThatThrownBy(() -> getEnrollmentByPaidSession(session))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("모집중인 강의가 아닙니다.");
    }

    private Session getRecruitingFreeSession() {
        Session session = FixtureSessionFactory.createFreeSession(1L);
        session.startRecruitment();
        return session;
    }

    private Enrollment getEnrollmentByPaidSession(Session session) {
        Payment mockPayment = FixturePaymentFactory.create(1L, 1L, 1L, 5_000L);
        return FixtureEnrollmentFactory.createEnrollmentByPaidSession(session, mockPayment);
    }
}