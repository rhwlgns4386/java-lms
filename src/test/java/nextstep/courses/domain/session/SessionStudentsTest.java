package nextstep.courses.domain.session;

import nextstep.qna.SessionException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionStudentsTest {

    @Test
    void 중복_수강_신청_검증() {

        SessionStudents sessionStudents = new SessionStudents(SessionType.PAID, 20);
        sessionStudents.registration(NsUserTest.JAVAJIGI);

        assertThatThrownBy(
                () -> sessionStudents.registration(NsUserTest.JAVAJIGI)
        ).isInstanceOf(SessionException.class);
    }

    @Test
    void 최대_수강_인원_넘어가면_예외() {

        SessionStudents sessionStudents = new SessionStudents(SessionType.PAID, 1);
        sessionStudents.registration(NsUserTest.JAVAJIGI);

        assertThatThrownBy(
                () -> sessionStudents.registration(NsUserTest.SANJIGI)
        ).isInstanceOf(SessionException.class);
    }
}
