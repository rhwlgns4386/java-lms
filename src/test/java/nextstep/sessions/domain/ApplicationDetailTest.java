package nextstep.sessions.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ApplicationDetailTest {
    private  ApplicationDetail A1 ;

    @BeforeEach
    void setUp() {
        A1 = ApplicationDetail.ofNewInstance(Long.valueOf(123), Long.valueOf(201));
    }


    @Test
    void isPresent_true() {

        assertThat(A1.isPresent(Long.valueOf(123), Long.valueOf(201))).isTrue();
    }

    @Test
    void isPresent_false() {
        assertThat(A1.isPresent(Long.valueOf(123), Long.valueOf(202))).isFalse();
    }

    @Test
    void approve_normal() {
        A1.approve();
        assertThat(A1.isValidStatus()).isTrue();
    }

    @Test
    void approve_fail() {
        A1.approve();
        assertThatThrownBy(() -> A1.approve()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void cancel() {

    }
}
