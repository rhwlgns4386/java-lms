package nextstep.courses.domain;

import nextstep.fixture.FreeSessionCreator;
import nextstep.fixture.PaidSessionCreator;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseTest {

    @Test
    @DisplayName("사용자가 신청한 강의목록을 반환한다.")
    void 사용자의_강의목록_반환() {
        PaidSession paidSession = PaidSessionCreator.user(1L);
        FreeSession freeSession = FreeSessionCreator.user(1L);
        Course course = new Course(List.of(paidSession, freeSession));
        assertThat(course.getSessions(NsUserTest.JAVAJIGI)).isEqualTo(List.of(paidSession, freeSession));
    }
}
