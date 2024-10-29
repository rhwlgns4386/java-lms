package nextstep.courses.domain;

import nextstep.courses.domain.SessionImage.ImageCapacity;
import nextstep.courses.domain.SessionImage.ImageSize;
import nextstep.courses.domain.SessionImage.ImageType;
import nextstep.courses.domain.SessionImage.SessionImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ImageCapacityTest {

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
    public void 이미지크기는_1MB이하_테스트() {
        assertDoesNotThrow(() -> {
            sessionImage = new SessionImage(imageCapacity,imageType,imageSize);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0,2})
    public void 이미지크기는_1MB제외실패_테스트(int imageCapacity) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            this.imageCapacity = new ImageCapacity(imageCapacity);
            SessionImage sessionImage = new SessionImage(this.imageCapacity,imageType,imageSize);
        });
    }
}
