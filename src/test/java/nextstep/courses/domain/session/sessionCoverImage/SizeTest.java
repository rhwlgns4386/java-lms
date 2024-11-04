package nextstep.courses.domain.session.sessionCoverImage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SizeTest {

    @Test
    void create() {
        Size size = new Size(1_048_576L);
        Assertions.assertThat(size.getBytes()).isEqualTo(1_048_576L);
    }

    @Test
    @DisplayName("이미지의 size가 최대 크기 초과 시 IllegalArgumentException 가 발생한다.")
    void create_최대_크기_초과() {
        Assertions.assertThatThrownBy(() -> {
            Size size = new Size(1_048_577L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
