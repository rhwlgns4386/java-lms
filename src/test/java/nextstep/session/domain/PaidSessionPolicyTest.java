package nextstep.session.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.enrollment.domain.Enrollment;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.fixture.FixturePaymentFactory;
import nextstep.session.domain.fixture.FixtureSessionFactory;
import nextstep.users.domain.NsUserTest;

class PaidSessionPolicyTest {
    @Test
    @DisplayName("유료강의 수강신청 시 수강료와 결제금액이 일치하지 않는 경우 예외가 발생한다.")
    void throwExceptionWhenSessionFeeDoesNotMatchPaymentAmount() {
        Session session = getRecruitingPaidSession(1L);
        Payment payment = FixturePaymentFactory.create(1L, 1L, 1L, 10_000L);
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);

        assertThatThrownBy(() -> new PaidSessionPolicy().validatePolicy(enrollment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("결제금액이 수강료와 일치하지 않습니다.");
    }

    @Test
    @DisplayName("유료강의 수강신청 시 인원이 초과된 경우 예외가 발생한다.")
    void throwExceptionWhenOverCapacity() {
        Session session = getRecruitingPaidSession(1L);
        Payment payment = FixturePaymentFactory.create(1L, 1L, 1L, 5_000L);
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);
        enrollment.approve();

        Enrollment enrollment2 = Enrollment.paid(2L, session, NsUserTest.SANJIGI, payment);

        assertThatThrownBy(() -> new PaidSessionPolicy().validatePolicy(enrollment2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("정원을 초과했습니다.");
    }

    @Test
    @DisplayName("유료강의 최대 수강 인원이 1명 이하일 때 예외가 발생한다.")
    void throwExceptionWhenCapacityLessThanZero() {
        Long invalidStudentCapacity = 0L;
        Session session = getRecruitingPaidSession(invalidStudentCapacity);
        Payment payment = FixturePaymentFactory.create(1L, 1L, 1L, 5_000L);
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);

        assertThatThrownBy(() -> new PaidSessionPolicy().validatePolicy(enrollment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("최대 수강 인원은 1명 이상이어야 합니다.");
    }

    private static Session getRecruitingPaidSession(Long studentCapacity) {
        Session session = FixtureSessionFactory.createPaidSession(1L, studentCapacity, 5000L);
        session.startRecruitment();
        return session;
    }
}