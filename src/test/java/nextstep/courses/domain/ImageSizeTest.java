package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageSizeTest {

    @Test
    void 생성() {
        ImageSize imageSize = new ImageSize(300, 200);
        assertThat(imageSize).isEqualTo(new ImageSize(300, 200));
    }

    @Test
    void width는_300이상이어야_한다() {
        assertThatThrownBy(() -> new ImageSize(12, 200))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void height는_200이상이어야_한다() {
        assertThatThrownBy(() -> new ImageSize(300, 20))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void width와_height의_비율은_3대2여야_한다() {
        assertThatThrownBy(() -> new ImageSize(300, 300))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
