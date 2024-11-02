package nextstep.courses.domain.session;

import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SessionStatusTest {

    @Test
    void can_register_session_if_recruit() {
        SessionStatus progressAndRecruit = new SessionStatus(SessionState.OPEN, RecruitState.RECRUIT);
        SessionStatus prepareAndRecruit = new SessionStatus(SessionState.OPEN, RecruitState.RECRUIT);

        assertThat(prepareAndRecruit.canRegister()).isTrue();
        assertThat(progressAndRecruit.canRegister()).isTrue();
    }

    @Test
    void cannot_register_session_if_not_recruit() {
        SessionStatus progressAndNotRecruit = new SessionStatus(SessionState.OPEN, RecruitState.NOT_RECRUIT);
        SessionStatus prepareAndNotRecruit = new SessionStatus(SessionState.PREPARING, RecruitState.NOT_RECRUIT);

        assertThat(progressAndNotRecruit.canRegister()).isFalse();
        assertThat(prepareAndNotRecruit.canRegister()).isFalse();
    }

    @Test
    void throw_exception_create_session_status_if_end_recruit() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SessionStatus(SessionState.END, RecruitState.RECRUIT));
    }
}
