package nextstep.users.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.courses.domain.RegisterStatus;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionStudent;
import nextstep.courses.domain.SessionStudentStatus;
import nextstep.courses.domain.SessionTestFixture;
import nextstep.courses.domain.Sessions;
import nextstep.courses.domain.Students;

class NsTeacherTest {

    @DisplayName("강사가 본인 강의에 선발된 인원을 추가한다")
    @Test
    void findRegisterStudentsOwnSession() {
        List<SessionStudent> sessionStudents = List.of(
            new SessionStudent(1L, 1L, SessionStudentStatus.PASS),
            new SessionStudent(1L, 2L, SessionStudentStatus.FAIL),
            new SessionStudent(1L, 3L, SessionStudentStatus.REGISTERED),
            new SessionStudent(1L, 4L, SessionStudentStatus.CANCELLED)
        );

        List<Session> sessionList = List.of(
            SessionTestFixture.createFreeSession(1L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(2L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(3L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(4L, SessionStatus.PREPARE, RegisterStatus.REGISTER)
        );
        Sessions sessions = new Sessions(sessionList);

        NsTeacher teacher = new NsTeacher("testTeacher", sessions);
        List<Long> registerStudents = teacher.findRegisterStudents(1L, sessionStudents);

        assertThat(registerStudents).hasSize(1);
        assertThat(registerStudents).containsExactlyInAnyOrder(1L);
    }

    @DisplayName("강사의 강의가 아닌 선발인원을 찾아내려고 하면 에러가 발생한다")
    @Test
    void findStudentsNotMineSession() {
        List<SessionStudent> sessionStudents = List.of(
            new SessionStudent(1L, 1L, SessionStudentStatus.PASS),
            new SessionStudent(1L, 2L, SessionStudentStatus.FAIL),
            new SessionStudent(1L, 3L, SessionStudentStatus.REGISTERED),
            new SessionStudent(1L, 4L, SessionStudentStatus.CANCELLED)
        );

        List<Session> sessionList = List.of(
            SessionTestFixture.createFreeSession(2L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(3L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(4L, SessionStatus.PREPARE, RegisterStatus.REGISTER)
        );
        Sessions sessions = new Sessions(sessionList);

        NsTeacher teacher = new NsTeacher("testTeacher", sessions);

        assertThatThrownBy(() -> teacher.findRegisterStudents(1L, sessionStudents))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("강사의 강의가 아닌 강의에 학생을 추가하려고 하면 에러가 발생한다")
    @Test
    void addStudentNotMineSession() {
        List<NsUser> studentList = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI,
            NsUserTest.GYEONGJAE
        );
        Students students = new Students(1L, studentList);

        List<Session> sessionList = List.of(
            SessionTestFixture.createFreeSession(2L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(3L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(4L, SessionStatus.PREPARE, RegisterStatus.REGISTER)
        );
        Sessions sessions = new Sessions(sessionList);

        NsTeacher teacher = new NsTeacher("testTeacher", sessions);

        assertThatThrownBy(() -> teacher.addStudent(1L, students, List.of()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("강사가 강의에 학생을 추가한다")
    @Test
    void addRegisterStudentOwnSession() {
        List<NsUser> studentList = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI,
            NsUserTest.GYEONGJAE
        );
        Students students = new Students(2L, studentList);

        List<Session> sessionList = List.of(
            SessionTestFixture.createFreeSession(2L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(3L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(4L, SessionStatus.PREPARE, RegisterStatus.REGISTER)
        );
        Sessions sessions = new Sessions(sessionList);

        NsTeacher teacher = new NsTeacher("testTeacher", sessions);
        teacher.addStudent(2L, students, List.of());

        assertThat(teacher.getSessions().size()).isEqualTo(3);
    }

    @DisplayName("강사가 선발되지 않은 학생들을 찾는다")
    @Test
    void findCancelStudentOwnSession() {
        List<SessionStudent> sessionStudents = List.of(
            new SessionStudent(1L, 1L, SessionStudentStatus.PASS),
            new SessionStudent(1L, 2L, SessionStudentStatus.FAIL),
            new SessionStudent(1L, 3L, SessionStudentStatus.FAIL),
            new SessionStudent(1L, 4L, SessionStudentStatus.FAIL)
        );

        List<Session> sessionList = List.of(
            SessionTestFixture.createFreeSession(1L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(2L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(3L, SessionStatus.PREPARE, RegisterStatus.REGISTER),
            SessionTestFixture.createFreeSession(4L, SessionStatus.PREPARE, RegisterStatus.REGISTER)
        );
        Sessions sessions = new Sessions(sessionList);

        NsTeacher teacher = new NsTeacher("testTeacher", sessions);
        List<Long> cancelStudents = teacher.findCancelStudents(1L, sessionStudents);

        assertThat(cancelStudents).hasSize(3);
        assertThat(cancelStudents).containsExactlyInAnyOrder(2L, 3L, 4L);
    }
}
