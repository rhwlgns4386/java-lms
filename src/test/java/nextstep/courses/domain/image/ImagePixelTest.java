package nextstep.courses.domain.image;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImagePixelTest {
    @Test
    @DisplayName("ImageProperty 생성")
    void createImagePropertyTest() {
        int width = 300;
        int height = 200;

        ImagePixel imagePixel = new ImagePixel(width, height);

        Assertions.assertThat(imagePixel).isNotNull();
    }

    @Test
    @DisplayName("ImageProperty 최소 width 체크")
    void checkMinWidthTest() {
        int width = 299;
        int height = 200;

        Assertions.assertThatThrownBy(() -> new ImagePixel(width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("ImageProperty 최소 height 체크")
    void checkMinHeightTest() {
        int width = 300;
        int height = 199;

        Assertions.assertThatThrownBy(() -> new ImagePixel(width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("ImageProperty width/height ratio 체크")
    void checkWidthHeightRatioTest() {
        int width = 300;
        int height = 300;

        Assertions.assertThatThrownBy(() -> new ImagePixel(width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
