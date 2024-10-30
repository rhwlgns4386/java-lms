package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageMetaDataTest {

    @DisplayName("1MB 이하의 허용된 확장자면 생성된다")
    @Test
    void create() {
        ImageMetaData metaInfo = new ImageMetaData(1, Extension.JPG);

        assertThat(metaInfo).isEqualTo(new ImageMetaData(1, Extension.JPG));
    }

    @DisplayName("이미지 크기가 1MB를 넘으면 예외를 발생한다")
    @Test
    void verifyImageSize() {
        assertThatThrownBy(() -> new ImageMetaData(2, Extension.JPG))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
