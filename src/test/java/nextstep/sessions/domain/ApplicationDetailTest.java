package nextstep.sessions.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ApplicationDetailTest {
    private static ApplicationDetail A1 = ApplicationDetail.ofNewInstance(Long.valueOf(123), Long.valueOf(201));

    @Test
    void isPresent_true() {
        assertThat(A1.isPresent(Long.valueOf(123), Long.valueOf(201))).isTrue();
    }

    @Test
    void isPresent_false() {
        assertThat(A1.isPresent(Long.valueOf(123), Long.valueOf(202))).isFalse();
    }
}
