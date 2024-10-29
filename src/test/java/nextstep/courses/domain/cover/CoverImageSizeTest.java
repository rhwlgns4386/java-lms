package nextstep.courses.domain.cover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoverImageSizeTest {
    @DisplayName("width는 300픽셀, height는 200픽셀 이상이 아니면 예외로 처리한다.")
    @Test
    void create_invalidSize() {
        assertThatThrownBy(() -> new CoverImageSize(299, 200))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @DisplayName("width, height 비율은 3:2가 아니면 예외로 처리한다.")
    @Test
    void create_invalidRatio() {
        assertThatThrownBy(() -> new CoverImageSize(299, 200))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
