package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageMetaInfoTest {

    @Test
    void 생성() {
        ImageMetaInfo metaInfo = new ImageMetaInfo(1, Extension.JPG);

        assertThat(metaInfo).isEqualTo(new ImageMetaInfo(1, Extension.JPG));
    }

    @Test
    void 이미지_크기는_1MB_이하여야한다() {
        assertThatThrownBy(() -> new ImageMetaInfo(2, Extension.JPG))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
