package nextstep.courses.domain.image;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImageExtensionTest {
    @ParameterizedTest
    @ValueSource(strings = {"1.gif", "1.jpg", "1.jpeg", "1.png", "1.svg"})
    void 이미지_확장자_생성(String value) {
        ImageExtension imageExtension = ImageExtension.from(value);
        assertThat(imageExtension).isNotNull();
    }

    @Test
    void 이미지_확장자_예외처리() {
        assertThatThrownBy(() -> ImageExtension.from("value.jjj"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 확장자입니다.");
    }

    @Test
    void 이미지_확장자_유무_예외처리() {
        assertThatThrownBy(() -> ImageExtension.from("value"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("확장자가 존재하지 않습니다.");
    }
}
