package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentManagerTest {
    @Test
    @DisplayName("StudentManager 생성")
    void createStudentManagerTest() {
        StudentManager studentManager = new StudentManager();

        Assertions.assertThat(studentManager).isNotNull();
    }

    @Test
    @DisplayName("StudentManager에 학생 한 명 추가")
    void addAStudentTest() {
        StudentManager studentManager = new StudentManager();

        studentManager.addStudent(NsUserTest.JAVAJIGI);

        Assertions.assertThat(studentManager.getStudentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("StudentManager에 학생 두 명 추가")
    void addTwoStudentTest() {
        StudentManager studentManager = new StudentManager();

        studentManager.addStudents(NsUserTest.JAVAJIGI, NsUserTest.SANJIGI);

        Assertions.assertThat(studentManager.getStudentCount()).isEqualTo(2);
    }
}
