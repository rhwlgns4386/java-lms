package nextstep.courses.domain.session.enrollment;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class StudentsTest {

    @Test
    void create_정상케이스() {
        Students students = new Students(10);

        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.countOfStudent()).isEqualTo(0);
    }

    @Test
    @DisplayName("최대 수강 인원을 초과한 경우 IllegalArgumentException이 발생한다.")
    void create_최대_수강_인원_초과() {
        NsUser user1 = NsUserTest.JAVAJIGI;
        NsUser user2 = NsUserTest.SANJIGI;

        Assertions.assertThatThrownBy(() -> {
            Students students = new Students(1, Arrays.asList(user1, user2));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enroll() {
        NsUser user1 = NsUserTest.JAVAJIGI;
        NsUser user2 = NsUserTest.SANJIGI;

        Students students = new Students(10);

        students.enroll(user1);
        students.enroll(user2);

        Assertions.assertThat(students.countOfStudent()).isEqualTo(2);
    }

    @Test
    @DisplayName("현재 학생 수와 최대 수강 인원이 동일한 경우 true를 반환한다.")
    void is_full() {
        NsUser user1 = NsUserTest.JAVAJIGI;
        NsUser user2 = NsUserTest.SANJIGI;

        Students students = new Students(2, Arrays.asList(user1, user2));
        Assertions.assertThat(students.isFull()).isTrue();
    }

    @Test
    @DisplayName("등록된 학생 수를 반환한다.")
    void countOfStudent() {
        NsUser user1 = NsUserTest.JAVAJIGI;
        NsUser user2 = NsUserTest.SANJIGI;

        Students students = new Students(2, Arrays.asList(user1, user2));

        Assertions.assertThat(students.countOfStudent()).isEqualTo(2);
    }

    @Test
    @DisplayName("이미 등록된 학생인 경우 true를 반환한다.")
    void alreadyEnrolled() {
        NsUser user1 = NsUserTest.JAVAJIGI;
        NsUser user2 = NsUserTest.SANJIGI;

        Students students = new Students(2, Arrays.asList(user1, user2));

        Assertions.assertThat(students.alreadyEnrolled(user1)).isTrue();
    }
}
