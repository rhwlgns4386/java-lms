package nextstep.sessions.domain;

import nextstep.sessions.SessionState;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionStateTest {

    @Test
    public void 세션상태_READY_테스트() {
        SessionState state = SessionState.READY;

        assertThat(state.isOpen()).isFalse();
        assertThat(state).isEqualTo(SessionState.READY);
    }

    @Test
    public void 세션상태_OPEN_테스트() {
        SessionState state = SessionState.OPEN;

        assertThat(state.isOpen()).isTrue();
        assertThat(state).isEqualTo(SessionState.OPEN);
    }

    @Test
    public void 세션상태_CLOSE_테스트() {
        SessionState state = SessionState.CLOSE;

        assertThat(state.isOpen()).isFalse();
        assertThat(state).isEqualTo(SessionState.CLOSE);
    }
}
