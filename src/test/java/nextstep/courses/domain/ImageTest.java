package nextstep.courses.domain;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImagePixel;
import nextstep.courses.domain.image.ImageSize;
import nextstep.courses.domain.image.ImageType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImageTest {
    private ImageSize imageSize;
    private ImagePixel imagePixel;
    private ImageType imageType;
    private Long imageId;

    @BeforeEach
    void init() {
        this.imageSize = new ImageSize(1024);
        this.imagePixel = new ImagePixel(300L, 200L);
        this.imageType = ImageType.JPG;
        this.imageId = 1L;
    }

    @Test
    @DisplayName("Image class 생성")
    void createImageTest() {
        Image image = new Image(imageId, imageSize, imageType, imagePixel);

        Assertions.assertThat(image).isNotNull();
        Assertions.assertThat(image.getId()).isEqualTo(imageId);
    }

    @Test
    @DisplayName("Image size 체크")
    void checkImageSizeTest() {
        Assertions.assertThatThrownBy(() -> new Image(imageId, new ImageSize(1025), imageType, imagePixel))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("image min width 체크")
    void checkMinImageWidthTest() {
        Assertions.assertThatThrownBy(() -> new Image(imageId, imageSize, imageType, new ImagePixel(299L, 200L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("image min height 체크")
    void checkMinImageHeightTest() {
        Assertions.assertThatThrownBy(() -> new Image(imageId, imageSize, imageType, new ImagePixel(300L, 199L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Image width,height 비율 체크")
    void checkImageWidthHeightRatioTest() {
        Assertions.assertThatThrownBy(() -> new Image(imageId, imageSize, imageType, new ImagePixel(299L, 199L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
