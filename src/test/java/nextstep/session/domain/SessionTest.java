package nextstep.session.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.enrollment.domain.Enrollment;
import nextstep.session.domain.fixture.FixtureSessionFactory;
import nextstep.users.domain.NsUserTest;

class SessionTest {
    @Test
    @DisplayName("Session 가 진행중 상태로 생성된다.")
    void initCourseStatusTest() {
        Session freeSession = FixtureSessionFactory.createFreeSession(1L);
        Session paidSession = FixtureSessionFactory.createPaidSession(2L, 1L, 5_000L);

        assertThat(freeSession.getCourseStatus()).isEqualTo(SessionProgressStatus.PREPARING);
        assertThat(paidSession.getCourseStatus()).isEqualTo(SessionProgressStatus.PREPARING);
    }

    @Test
    @DisplayName("startSession 메서드가 강의 진행 상태를 모집중으로 변경한다.")
    void startSessionTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.createFreeSession(1L, "title", startAt, endAt, null);

        session.startSession();

        assertThat(session.getCourseStatus()).isEqualTo(SessionProgressStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("수강신청 시 수강인원이 증가한다.")
    void incrementEnrollStudentCountWhenEnrollTest() {
        Session session = Session.createFreeSession(1L, "title", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1),
            null);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);

        session.startSession();
        session.enroll(enrollment);

        assertThat(session.getEnrolledUserCount()).isOne();
    }

    @Test
    @DisplayName("수강신청 시 수강 신청 상태가 모집중이 아닐 경우 예외가 발생한다.")
    void resTest() {
        Session session = Session.createPaidSession(1L, "title", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 1),
            null, 1L,
            10_000L);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);

        assertThatThrownBy(() -> session.enroll(enrollment)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("모집중 상태의 강의가 아닙니다.");
    }

}