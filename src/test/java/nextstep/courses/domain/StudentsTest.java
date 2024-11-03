package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

public class StudentsTest {

    @DisplayName("최대 수강 학생 수와 현재 학생수를 비교한다")
    @Test
    void checkStudentSizeWithMaxSize() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(1L, students);

        boolean result = studentList.isBigger(5);

        assertThat(result).isFalse();
    }

    @DisplayName("학생을 추가한다")
    @Test
    void registerOnSession() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(1L, students);

        studentList.add(NsUserTest.GYEONGJAE);

        assertThat(studentList.size()).isEqualTo(3);
    }

    @DisplayName("SessionStudent 목록에 있는 학생들의 상태를 REGISTERED로 변경한다")
    @Test
    void toRegistered() {
        List<NsUser> studentList = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students students = new Students(1L, studentList);

        SessionStudent sut = new SessionStudent(1L, 1L, SessionStudentStatus.PASS);
        List<SessionStudent> sessionStudents = List.of(
            sut,
            new SessionStudent(1L, 2L, SessionStudentStatus.PASS)
        );

        students.toRegistered(sessionStudents);

        assertThat(sut).isEqualTo(new SessionStudent(1L, 1L, SessionStudentStatus.REGISTERED));
    }

    @DisplayName("SessionStudent 목록에 있는 학생들의 상태를 CANCELED로 변경한다")
    @Test
    void toCanceled() {
        List<NsUser> studentList = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students students = new Students(1L, studentList);

        SessionStudent sut = new SessionStudent(1L, 1L, SessionStudentStatus.FAIL);
        List<SessionStudent> sessionStudents = List.of(
            sut,
            new SessionStudent(1L, 2L, SessionStudentStatus.FAIL)
        );

        students.toCancel(sessionStudents);

        assertThat(sut).isEqualTo(new SessionStudent(1L, 1L, SessionStudentStatus.CANCELLED));
    }
}
