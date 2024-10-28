package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImageTest {
    private ImageSize imageSize;
    private ImageProperty imageProperty;
    private ImageType imageType;

    @BeforeEach
    void init() {
        this.imageSize = new ImageSize(1024);
        this.imageProperty = new ImageProperty(300L, 200L);
        this.imageType = ImageType.JPG;
    }

    @Test
    @DisplayName("Image class 생성")
    void createImageTest() {
        Long id = 1L;
        ImageType imageType = ImageType.JPG;

        Image image = new Image(id, imageSize, imageType, imageProperty);

        Assertions.assertThat(image).isNotNull();
        Assertions.assertThat(image.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Image size 체크")
    void checkImageSizeTest() {
        Long id = 1L;

        Assertions.assertThatThrownBy(() -> new Image(id, imageSize, imageType, imageProperty))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("image min width 체크")
    void checkMinImageWidthTest() {
        Long id = 1L;

        Assertions.assertThatThrownBy(() -> new Image(id, imageSize, imageType, imageProperty))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("image min height 체크")
    void checkMinImageHeightTest() {
        Long id = 1L;

        Assertions.assertThatThrownBy(() -> new Image(id, imageSize, imageType, imageProperty))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Image width,height 비율 체크")
    void checkImageWidthHeightRatioTest() {
        Long id = 1L;

        Assertions.assertThatThrownBy(() -> new Image(id, imageSize, imageType, imageProperty))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
