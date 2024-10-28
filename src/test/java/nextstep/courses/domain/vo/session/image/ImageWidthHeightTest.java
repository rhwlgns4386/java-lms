package nextstep.courses.domain.vo.session.image;

import nextstep.courses.ImageWidthHeightRatioMismatchException;
import nextstep.courses.WidthHeightMinimumException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static nextstep.courses.domain.vo.session.image.ImageWidthHeight.IMAGE_DIMENSION_MISMATCH_MESSAGE;
import static nextstep.courses.domain.vo.session.image.ImageWidthHeight.WIDTH_HEIGHT_MINIMUM_MESSAGE;
import static org.assertj.core.api.Assertions.*;

public class ImageWidthHeightTest {

    @Test
    void create() {
        int width = 300;
        int height = 200;
        ImageWidthHeight actual = new ImageWidthHeight(width, height);
        ImageWidthHeight expected = new ImageWidthHeight(300, 200);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest(name = "create_가로세로_최소_300_200: {0}_{1}")
    @CsvSource(value = {
            "299, 200",
            "300, 199"})
    void create_가로세로_최소_300_200_오류(int width, int height) {
        assertThatThrownBy(() -> {
                    new ImageWidthHeight(width, height);
                }).isInstanceOf(WidthHeightMinimumException.class)
                .hasMessage(WIDTH_HEIGHT_MINIMUM_MESSAGE);
    }

    @ParameterizedTest(name = "create_가로세로_비율_3_2: {0}_{1}")
    @CsvSource(value = {
            "350, 200",
            "400, 300"
    })
    void create_가로세로_비율_3_2_불일치(int width, int height) {
        assertThatThrownBy(() -> {
                    new ImageWidthHeight(width, height);
                }).isInstanceOf(ImageWidthHeightRatioMismatchException.class)
                .hasMessage(IMAGE_DIMENSION_MISMATCH_MESSAGE);
    }

}
