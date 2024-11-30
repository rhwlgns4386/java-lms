package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.ImageSizeMissException;
import org.junit.jupiter.api.Test;

public class ImageByteTest {

    @Test
    void 이미지바이트는_1MB이하인경우_정상_처리() {
        assertThatCode(() -> new ImageByte(1024 * 1024)).doesNotThrowAnyException();
    }

    @Test
    void 이미지바이트는_1MB초과인경우_예외() {
        assertThatThrownBy(() -> new ImageByte(1024 * 1024 + 1)).isInstanceOf(ImageSizeMissException.class);
    }
}
