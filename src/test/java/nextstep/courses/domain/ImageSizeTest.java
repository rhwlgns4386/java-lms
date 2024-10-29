package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ImageSizeTest {
    @Test
    @DisplayName("이미지 크기 검증")
    void validateSizeOfImage() {
        assertThatThrownBy(() -> new ImageSize(1024 * 1025)).isInstanceOf(IllegalArgumentException.class);
    }
}
