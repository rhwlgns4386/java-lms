package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionStateTest {

    @Test
    void create() {
        assertThat(SessionState.CLOSED).isEqualTo(SessionState.CLOSED);
    }

}
