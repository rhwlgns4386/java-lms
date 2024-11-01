package nextstep.courses.domain.session;

import nextstep.courses.SessionException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionStudentsTest {

    @Test
    void 중복_수강_신청_검증() {

        SessionStudents sessionStudents = new SessionStudents(20);
        sessionStudents.registration(1L, NsUserTest.JAVAJIGI);

        assertThatThrownBy(
                () -> sessionStudents.registration(1L, NsUserTest.JAVAJIGI)
        ).isInstanceOf(SessionException.class);
    }

    @Test
    void 최대_수강_인원_판별_false() {
        SessionStudents sessionStudents = new SessionStudents(2);
        sessionStudents.registration(1L, NsUserTest.JAVAJIGI);

        assertThat(sessionStudents.isExceeds()).isFalse();
    }

    @Test
    void 최대_수강_인원_판별_true() {
        SessionStudents sessionStudents = new SessionStudents(2);
        sessionStudents.registration(1L, NsUserTest.JAVAJIGI);
        sessionStudents.registration(1L, NsUserTest.SANJIGI);

        assertThat(sessionStudents.isExceeds()).isTrue();
    }
}
