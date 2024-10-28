package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

public class StudentsTest {

    @Test
    void 학생수_제한이_있다() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(students, 2);

        assertThatThrownBy(() -> studentList.add(NsUserTest.GYEONGJAE))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 무료강의라_제한이없는_경우_추가가_성공한다() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(students);

        studentList.add(NsUserTest.GYEONGJAE);

        assertThat(studentList.size()).isEqualTo(3);
    }
}
