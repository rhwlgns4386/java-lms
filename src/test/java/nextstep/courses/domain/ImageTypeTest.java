package nextstep.courses.domain;

import nextstep.courses.domain.SessionImage.ImageCapacity;
import nextstep.courses.domain.SessionImage.ImageSize;
import nextstep.courses.domain.SessionImage.ImageType;
import nextstep.courses.domain.SessionImage.SessionImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ImageTypeTest {


    public static final int IMAGE_CAPACITY = 1;
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    public static final String JPG = "jpg";
    ImageCapacity imageCapacity;
    ImageSize imageSize;
    ImageType imageType;
    SessionImage sessionImage;
    @BeforeEach
    public void setUp() {

        imageCapacity = new ImageCapacity(IMAGE_CAPACITY);
        imageSize = new ImageSize(WIDTH, HEIGHT);
        imageType = ImageType.jpg;

    }

    @Test
    public void 이미지타입_성공_테스트() {

        assertDoesNotThrow(() -> {
            SessionImage sessionImage = new SessionImage(imageCapacity, imageType,imageSize);
        });
    }

    @Test
    public void 이미지타입_오입력_실패_테스트() {
        String IMAGE_TYPE = "git";
        assertThatIllegalArgumentException().isThrownBy(() -> {
            imageType = ImageType.validateType(IMAGE_TYPE);
        });
    }
}
