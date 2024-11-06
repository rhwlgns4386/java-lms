package nextstep.session.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.enrollment.domain.Enrollment;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

class SessionTest {
    @Test
    @DisplayName("Session 가 진행중 상태로 생성된다.")
    void initCourseStatusTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session freeSession = Session.createFreeSession(1L, "title", startAt, endAt, null);
        Session paidSession = Session.createPaidSession(1L, "title", startAt, endAt, null, 1, 10_000L);

        assertThat(freeSession.getCourseStatus()).isEqualTo(SessionStatus.PENDING);
        assertThat(paidSession.getCourseStatus()).isEqualTo(SessionStatus.PENDING);
    }

    @Test
    @DisplayName("open 메서드가 강의 상태를 모집중으로 변경한다.")
    void openTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.createFreeSession(1L, "title", startAt, endAt, null);

        session.open();

        assertThat(session.getCourseStatus()).isEqualTo(SessionStatus.OPEN);
    }

    @Test
    @DisplayName("유료강의 수강신청 시 인원이 초과된 경우 예외가 발생한다.")
    void throwExceptionWhenOverCapacity() {
        Session session = Session.createPaidSession(1L, "title", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1),
            null, 1,
            10_000L);
        Payment payment = new Payment("payment_id", session.getId(), NsUserTest.JAVAJIGI.getId(), 10_000L);
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);

        session.open();
        session.enroll(enrollment);

        assertThatThrownBy(() -> session.enroll(enrollment)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("정원을 초과했습니다.");
    }

    @Test
    @DisplayName("유료강의 수강신청 시 수강료와 결제금액이 일치하지 않는 경우 예외가 발생한다.")
    void throwExceptionWhenSessionFeeDoesNotMatchPaymentAmount() {
        Session session = Session.createPaidSession(1L, "title", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1),
            null, 1,
            10_000L);
        Payment payment = new Payment("payment_id", session.getId(), NsUserTest.JAVAJIGI.getId(), 2_000L);
        Enrollment enrollment = Enrollment.paid(1L, session, NsUserTest.JAVAJIGI, payment);

        session.open();

        assertThatThrownBy(() -> session.enroll(enrollment)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("결제금액이 수강료와 일치하지 않습니다.");
    }

    @Test
    @DisplayName("수강신청 시 수강인원이 증가한다.")
    void incrementEnrollStudentCountWhenEnrollTest() {
        Session session = Session.createFreeSession(1L, "title", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1),
            null);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);

        session.open();
        session.enroll(enrollment);

        assertThat(session.getEnrolledUserCount()).isOne();
    }

    @Test
    @DisplayName("수강신청 시 모집중 상태가 아닐 경우 예외가 발생한다.")
    void resTest() {
        Session session = Session.createPaidSession(1L, "title", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1),
            null, 1,
            10_000L);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);

        assertThatThrownBy(() -> session.enroll(enrollment)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("모집중 상태의 강의가 아닙니다.");
    }

}