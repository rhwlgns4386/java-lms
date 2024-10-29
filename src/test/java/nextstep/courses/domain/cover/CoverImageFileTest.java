package nextstep.courses.domain.cover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoverImageFileTest {
    @DisplayName("강의 커버 이미지가 1MB보다 크면 예외로 처리한다.")
    @Test
    void sizeException() {
        assertThatThrownBy(() -> new CoverImageFile(1024 * 1025))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
