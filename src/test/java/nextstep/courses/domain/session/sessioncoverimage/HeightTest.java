package nextstep.courses.domain.session.sessioncoverimage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeightTest {

    @Test
    @DisplayName("height의 최소 사이즈를 만족하지 않는 경우 IllegalArgumentException가 발생한다.")
    void create_최소_사이즈_미만() {
        Assertions.assertThatThrownBy(() -> {
            Height height = new Height(199);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getPx() {
        Height height = new Height(250);
        Assertions.assertThat(height.getPx()).isEqualTo(250);
    }

    @Test
    void pxByRatio() {
        Height height = new Height(250);
        int pxByRatio = height.pxByRatio(3);
        Assertions.assertThat(pxByRatio).isEqualTo(750);
    }
}
