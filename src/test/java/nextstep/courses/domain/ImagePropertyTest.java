package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImagePropertyTest {
    @Test
    @DisplayName("ImageProperty 생성")
    void createImagePropertyTest() {
        Long width = 300L;
        Long height = 200L;

        ImageProperty imageProperty = new ImageProperty(width, height);

        Assertions.assertThat(imageProperty).isNotNull();
    }

    @Test
    @DisplayName("ImageProperty 최소 width 체크")
    void checkMinWidthTest() {
        Long width = 299L;
        Long height = 200L;

        Assertions.assertThatThrownBy(() -> new ImageProperty(width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("ImageProperty 최소 height 체크")
    void checkMinHeightTest() {
        Long width = 300L;
        Long height = 199L;

        Assertions.assertThatThrownBy(() -> new ImageProperty(width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("ImageProperty width/height ratio 체크")
    void checkWidthHeightRatioTest() {
        Long width = 300L;
        Long height = 300L;

        Assertions.assertThatThrownBy(() -> new ImageProperty(width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
