package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageTest {

    @DisplayName("정상적인 사이즈, 메타 정보를 가지고 생성된다")
    @Test
    void create() {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaData imageMetaData = new ImageMetaData(1, Extension.JPG);
        Image sessionImage = new Image(imageSize, imageMetaData);
        assertThat(sessionImage).isEqualTo(new Image(imageSize, imageMetaData));
    }
}
