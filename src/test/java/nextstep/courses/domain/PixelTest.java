package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.NonPositiveException;
import org.junit.jupiter.api.Test;

public class PixelTest {

    @Test
    void 픽셀정상_생성() {
        assertThatCode(() -> new Pixel(0)).doesNotThrowAnyException();
    }

    @Test
    void 픽셀은_음수일_수없다() {
        assertThatThrownBy(() -> new Pixel(-1)).isInstanceOf(NonPositiveException.class);
    }
}
