package nextstep.session.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.enrollment.domain.Enrollment;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

class PaidSessionPolicyTest {
    @Test
    @DisplayName("유료강의 수강신청 시 수강료와 결제금액이 일치하지 않는 경우 예외가 발생한다.")
    void throwExceptionWhenSessionFeeDoesNotMatchPaymentAmount() throws Exception {
        Session session = Session.createPaidSession(
            1L,
            "title",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null,
            1L,
            5_000L);
        Payment payment = new Payment(1L, 1L, 1L, 10_000L, LocalDateTime.now());
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);

        assertThatThrownBy(() -> new PaidSessionPolicy().validatePolicy(session, enrollment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("결제금액이 수강료와 일치하지 않습니다.");
    }

    @Test
    @DisplayName("유료강의 수강신청 시 인원이 초과된 경우 예외가 발생한다.")
    void throwExceptionWhenOverCapacity() {
        Session session = Session.createPaidSession(
            1L,
            "title",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null,
            1L,
            5_000L);
        Payment payment = new Payment(1L, 1L, 1L, 10_000L, LocalDateTime.now());
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);
        session.open();
        session.enroll(enrollment);

        Enrollment enrollment2 = Enrollment.paid(2L, session, NsUserTest.SANJIGI, payment);

        assertThatThrownBy(() -> new PaidSessionPolicy().validatePolicy(session, enrollment2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("정원을 초과했습니다.");
    }

    @Test
    @DisplayName("유료강의 최대 수강 인원이 1명 이하일 때 예외가 발생한다.")
    void throwExceptionWhenCapacityLessThanZero() {
        Session session = Session.createPaidSession(
            1L,
            "title",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null,
            0L,
            5_000L);
        Payment payment = new Payment(1L, 1L, 1L, 10_000L, LocalDateTime.now());
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);

        assertThatThrownBy(() -> new PaidSessionPolicy().validatePolicy(session, enrollment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("최대 수강 인원은 1명 이상이어야 합니다.");
    }
}