package nextstep.courses.domain.session.sessionCoverImage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WidthTest {
    @Test
    @DisplayName("width의 최소 사이즈를 만족하지 않는 경우 IllegalArgumentException가 발생한다.")
    void create_최소_사이즈_미만() {
        Assertions.assertThatThrownBy(() -> {
            Width width = new Width(299);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getPx() {
        Width width = new Width(370);
        Assertions.assertThat(width.getPx()).isEqualTo(370);
    }

    @Test
    void pxByRatio() {
        Width width = new Width(370);
        int pxByRatio = width.pxByRatio(2);
        Assertions.assertThat(pxByRatio).isEqualTo(740);
    }
}
