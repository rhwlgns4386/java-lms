package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.courses.domain.PaidSessionTest.ps2;
import static nextstep.courses.domain.PaidSessionTest.ps3;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseTest {

    @Test
    @DisplayName("사용자가 신청한 강의목록을 반환한다.")
    void 사용자의_강의목록_반환() {
        Course course = new Course(List.of(ps2, ps3));
        assertThat(course.getSessions(NsUserTest.JAVAJIGI)).isEqualTo(List.of(ps2, ps3));
    }
}
