package nextstep.session.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Session session = FixtureSessionFactory.createFreeSession(1L);

        session.startSession();

        assertThat(session.getCourseStatus()).isEqualTo(SessionProgressStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("수강신청 시 수강인원이 증가한다.")
    void incrementEnrollStudentCountWhenEnrollTest() {
        Session session = FixtureSessionFactory.createFreeSession(1L);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);

        session.startRecruitment();
        session.enroll(enrollment);

        assertThat(session.getEnrolledUserCount()).isOne();
    }

    @Test
    @DisplayName("종료된 강의의 모집 상태를 변경할 때 예외가 발생한다.")
    void throwExceptionWhenUpdatingEnrollmentStatusForClosedSession() {
        Session session = FixtureSessionFactory.createFreeSession(1L);

        session.completeSession();

        assertThatThrownBy(() -> session.startRecruitment())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("종료된 강의는 모집 상태를 변경할 수 없습니다.");
    }

    @Test
    @DisplayName("강의 모집 상태가 모집중일 때 수강신청이 가능하다.")
    void enrollmentAllowedWhenSessionIsRecruiting() {
        Session session = FixtureSessionFactory.createFreeSession(1L);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);

        session.startRecruitment(); // 강의 모집 상태 모집중

        assertDoesNotThrow(() -> session.enroll(enrollment));
    }

    @Test
    @DisplayName("강의 모집 상태가 모집중이 아닐 때 수강신청시 예외가 발생한다.")
    void throwExceptionWhenSessionIsNotRecruiting() {
        Session session = FixtureSessionFactory.createFreeSession(1L);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);

        assertThatThrownBy(() -> session.enroll(enrollment))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("모집중 상태의 강의가 아닙니다.");
    }

}