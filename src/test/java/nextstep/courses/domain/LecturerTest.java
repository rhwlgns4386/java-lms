package nextstep.courses.domain;

import nextstep.courses.domain.Lecturer.Lecturer;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LecturerTest {
    public static final Lecturer testLecturer = new Lecturer(NsUserTest.SANJIGI.getId());

    @Test
    @DisplayName("Lecturer 생성")
    void createLecturerTest() {
        Lecturer lecturer = new Lecturer(NsUserTest.SANJIGI.getId());

        Assertions.assertThat(lecturer).isNotNull();
    }
}
