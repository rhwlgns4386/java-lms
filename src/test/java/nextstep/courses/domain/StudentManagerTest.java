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
    @DisplayName("StudentManager에 학생 추가")
    void addStudentsTest() {
        StudentManager studentManager = new StudentManager();

        studentManager.addStudents(NsUserTest.JAVAJIGI, NsUserTest.SANJIGI);

        Assertions.assertThat(studentManager.getStudentCount()).isEqualTo(2);
    }
}
