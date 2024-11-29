package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import nextstep.courses.ImageSizeMissException;
import org.junit.jupiter.api.Test;

public class ImageResolutionTest {

    @Test
    void 이미지최소_크기는_가로300_높이_200이다() {
        assertThatCode(() -> new ImageResolution(300, 200)).doesNotThrowAnyException();
    }

    @Test
    void 크기가_최소값_보다작은_경우_예외() {
        assertThatThrownBy(() -> new ImageResolution(297, 198)).isInstanceOf(ImageSizeMissException.class);
    }

    @Test
    void 이미지의_비율은_3대2가아닌경우_예외() {
        assertThatThrownBy(() -> new ImageResolution(600, 399)).isInstanceOf(ImageSizeMissException.class);
    }

    @Test
    void 크기는_null일수_없다() {
        assertAll(() -> {
            assertThatNullPointerException().isThrownBy(() -> new ImageResolution(null, new Pixel(300)));
            assertThatNullPointerException().isThrownBy(() -> new ImageResolution(new Pixel(300), null));
            assertThatNullPointerException().isThrownBy(() -> new ImageResolution(null, null));
        });
    }
}
