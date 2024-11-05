package nextstep.courses.domain.student;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    @DisplayName("Student class 생성")
    void createStudentTest() {
        NsUser sanjigi = NsUserTest.SANJIGI;
        Long amount = 100_000L;

        Student student = new Student(sanjigi.getId(), amount);

        Assertions.assertThat(student).isNotNull();
    }

    @Test
    @DisplayName("수강 승인")
    void acceptStudentTest() {
        NsUser sanjigi = NsUserTest.SANJIGI;
        Long amount = 100_000L;

        Student student = new Student(sanjigi.getId(), amount);

        student.accept();

        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getStatus()).isEqualTo(StudentStatus.ACCEPTED);
    }

    @Test
    @DisplayName("수강 취소")
    void rejectStudentTest() {
        NsUser sanjigi = NsUserTest.SANJIGI;
        Long amount = 100_000L;

        Student student = new Student(sanjigi.getId(), amount);

        student.reject();

        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getStatus()).isEqualTo(StudentStatus.REJECTED);
    }
}
