package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionImageTest {

    @DisplayName("Session id와 Image id를 가지고 SessionImage를 생성한다")
    @Test
    void create() {
        SessionImage sessionImage = new SessionImage(1L, 1L);
        assertThat(sessionImage).isEqualTo(new SessionImage(1L, 1L));
    }
}
