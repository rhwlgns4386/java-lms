package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUserTest;

class SessionTest {
    @Test
    @DisplayName("Session 가 진행중 상태로 생성된다.")
    void initCourseStatusTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session freeSession = Session.createFreeSession(startAt, endAt, null);
        Session paidSession = Session.createPaidSession(startAt, endAt, null, 1);

        assertThat(freeSession.getCourseStatus()).isEqualTo(SessionStatus.PENDING);
        assertThat(paidSession.getCourseStatus()).isEqualTo(SessionStatus.PENDING);
    }

    @Test
    @DisplayName("open 메서드가 강의 상태를 모집중으로 변경한다.")
    void openTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.createFreeSession(startAt, endAt, null);

        session.open();

        assertThat(session.getCourseStatus()).isEqualTo(SessionStatus.OPEN);
    }

    @Test
    @DisplayName("유료강의 수강신청 시 인원이 초과된 경우 예외가 발생한다.")
    void throwExceptionWhenOverCapacity() {
        Session session = Session.createPaidSession(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null,
            1);
        Enrollment enrollment = new Enrollment(1L, session, NsUserTest.JAVAJIGI, LocalDateTime.now());

        session.open();
        session.register(enrollment);

        assertThatThrownBy(() -> session.register(enrollment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("정원을 초과했습니다.");
    }

    @Test
    @DisplayName("수강신청 시 수강인원이 증가한다.")
    void incrementEnrollStudentCountWhenRegisterTest() {
        Session session = Session.createPaidSession(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null,
            1);
        Enrollment enrollment = new Enrollment(1L, session, NsUserTest.JAVAJIGI, LocalDateTime.now());

        session.open();
        session.register(enrollment);

        assertThat(session.getEnrolledUserCount()).isOne();
    }

    @Test
    @DisplayName("수강신청 시 모집중 상태가 아닐 경우 예외가 발생한다.")
    void resTest() {
        Session session = Session.createPaidSession(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null,
            1);
        Enrollment enrollment = new Enrollment(1L, session, NsUserTest.JAVAJIGI, LocalDateTime.now());

        assertThatThrownBy(() -> session.register(enrollment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("모집중 상태의 강의가 아닙니다.");
    }

}