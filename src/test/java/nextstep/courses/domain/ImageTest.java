package nextstep.courses.domain;

import nextstep.courses.domain.SessionImage.SessionImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ImageTest {

    @Test
    public void 이미지크기는_1MB이하_테스트() {
        int IMAGE_SIZE = 1;
        String IMAGE_TYPE = "gif";
        int WIDTH = 300;
        int HEIGHT = 200;
        assertDoesNotThrow(() -> {
            SessionImage sessionImage = new SessionImage(IMAGE_SIZE, IMAGE_TYPE, WIDTH, HEIGHT);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0,2})
    public void 이미지크기는_1MB제외실패_테스트(int imageSize) {
        String IMAGE_TYPE = "gif";
        int WIDTH = 300;
        int HEIGHT = 200;
        assertThatIllegalArgumentException().isThrownBy(() -> {
            SessionImage sessionImage = new SessionImage(imageSize, IMAGE_TYPE, WIDTH, HEIGHT);
        });
    }

    @Test
    public void 이미지타입_성공_테스트() {
        int IMAGE_SIZE = 1;
        String IMAGE_TYPE = "gif";
        int WIDTH = 300;
        int HEIGHT = 200;
        assertDoesNotThrow(() -> {
            SessionImage sessionImage = new SessionImage(IMAGE_SIZE, IMAGE_TYPE, WIDTH, HEIGHT);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0,2})
    public void 이미지타입_오입력_실패_테스트(int imageSize) {
        String IMAGE_TYPE = "git";
        int WIDTH = 300;
        int HEIGHT = 200;
        assertThatIllegalArgumentException().isThrownBy(() -> {
            SessionImage sessionImage = new SessionImage(imageSize, IMAGE_TYPE, WIDTH, HEIGHT);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"200,200","300,199","200,199","400,300"})
    public void 이미지타입_사이즈및비율실패_테스트(int width, int height) {
        int IMAGE_SIZE = 1;
        String IMAGE_TYPE = "gif";
        assertThatIllegalArgumentException().isThrownBy(() -> {
            SessionImage sessionImage = new SessionImage(IMAGE_SIZE, IMAGE_TYPE, width, height);
        });
    }





}
