package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStudentTest {

    @DisplayName("Session id와 NsUser id를 가지고 SessionStudent를 생성한다")
    @Test
    void create() {
        SessionStudent sessionStudent = new SessionStudent(1L, 1L);
        assertThat(sessionStudent).isEqualTo(new SessionStudent(1L, 1L));
    }
}
