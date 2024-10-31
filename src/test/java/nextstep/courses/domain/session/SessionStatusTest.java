package nextstep.courses.domain.session;

import nextstep.qna.SessionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionStatusTest {

    @Test
    void 수강중아니라면_수강신청시_예외() {
        SessionStatus ready = SessionStatus.READY;

        assertThatThrownBy(
                () -> ready.validateRegistration()
        ).isInstanceOf(SessionException.class);
    }
}
