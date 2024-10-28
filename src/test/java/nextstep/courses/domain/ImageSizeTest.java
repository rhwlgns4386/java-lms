package nextstep.courses.domain;

import nextstep.courses.domain.SessionImage.SessionImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ImageSizeTest {

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
