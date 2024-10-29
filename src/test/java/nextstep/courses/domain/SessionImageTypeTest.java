package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionImageTypeTest {

    @Test
    void create() {
        assertThat(SessionImageType.of("gif")).isEqualTo(SessionImageType.of("gif"));
    }

    @Test
    void 생성자_예외_테스트() {
        assertThatThrownBy(() -> {
            SessionImageType.of("3ds");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
