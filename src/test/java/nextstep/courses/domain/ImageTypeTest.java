package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageTypeTest {

    @Test
    @DisplayName("유효하지 않는 파일 형식 시 exception 반환")
    void invalid_file_type() {
        assertThatThrownBy(() -> {
            ImageType.fromString("jyp");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유효 않는 파일 형식 시 파일 타입 반환")
    void valid_file_type() {
        assertThat(ImageType.fromString("jpg")).isEqualTo(ImageType.JPG);
    }
}
