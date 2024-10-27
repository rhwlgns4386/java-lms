package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImageTest {
    @Test
    @DisplayName("Image class 생성")
    void createImageTest() {
        Long id = 1L;
        Long imageSize = 1000L; //KB
        ImageType imageType = ImageType.JPG;
        Long width = 300L;
        Long height = 200L;

        Image image = new Image(id, imageSize, imageType, width, height);

        Assertions.assertThat(image).isNotNull();
        Assertions.assertThat(image.getId()).isEqualTo(id);
    }
}
