package nextstep.courses.domain;

import nextstep.courses.domain.SessionImage.ImageCapacity;
import nextstep.courses.domain.SessionImage.ImageSize;
import nextstep.courses.domain.SessionImage.ImageType;
import nextstep.courses.domain.SessionImage.SessionImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ImageSizeTest {

    public static final int IMAGE_CAPACITY = 1;
    public static final String JPG = "jpg";
    ImageCapacity imageCapacity;
    ImageSize imageSize;
    ImageType imageType;
    SessionImage sessionImage;

    @BeforeEach
    public void setUp() {
        imageCapacity = new ImageCapacity(IMAGE_CAPACITY);
        imageType = ImageType.jpg;
    }

    @ParameterizedTest
    @CsvSource(value = {"200,200", "300,199", "200,199", "400,300"})
    public void 이미지타입_사이즈및비율실패_테스트(int width, int height) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            imageSize = new ImageSize(width, height);
            SessionImage sessionImage = new SessionImage(imageCapacity, imageType, imageSize);
        });
    }


}
