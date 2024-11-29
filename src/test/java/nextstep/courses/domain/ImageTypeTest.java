package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ImageTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"jpg", "jpeg", "gif", "png", "svg"})
    void 지원하지않는_이미지_타입이면_예외(String typeName) {
        assertThatCode(() -> ImageType.findByName(typeName)).doesNotThrowAnyException();
    }

    @Test
    void 지원하지않는_이미지_타입이면_예외() {
        assertThatIllegalArgumentException().isThrownBy(() -> ImageType.findByName("psd"));
    }
}
