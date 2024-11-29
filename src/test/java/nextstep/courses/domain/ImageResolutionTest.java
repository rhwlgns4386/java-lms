package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}
