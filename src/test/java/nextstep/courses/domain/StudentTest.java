package nextstep.courses.domain;

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
}
