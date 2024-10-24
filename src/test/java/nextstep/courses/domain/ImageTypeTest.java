package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.ImageType.GIF;

public class ImageTypeTest {

    @Test
    @DisplayName("해당하는 확장자가 없을 경우, 예외를 발생시킨다.")
    void 열거형_반환_예외() {
        Assertions.assertThatThrownBy(() -> ImageType.getExtension("mkv"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("스트링을 입력하면 해당 열거형으로 반환한다.")
    void 열거형_반환() {
        Assertions.assertThat(ImageType.getExtension("gif")).isEqualTo(GIF);
    }
}
