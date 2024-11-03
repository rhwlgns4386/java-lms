package nextstep.courses.domain.image;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ImageSizeTest {
    @Test
    void 이미지_사이즈_생성() {
        ImageSize imageSize = new ImageSize(300, 200, 1048);
        assertThat(imageSize).isNotNull();
    }

    @Test
    void 이미지_길이_검증() {
        assertThatThrownBy(() -> new ImageSize(210, 150, 1048))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미지_비율_검증() {
        assertThatThrownBy(() -> new ImageSize(400, 300, 1048))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
