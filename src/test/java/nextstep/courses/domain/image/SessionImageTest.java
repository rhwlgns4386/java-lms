package nextstep.courses.domain.image;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SessionImageTest {
    @Test
    void 강의_이미지_생성() {
        SessionImage sessionImage = new SessionImage("next.jpg", 300, 200, 1000);
        assertThat(sessionImage).isNotNull();
    }

    @Test
    void 강의_이미지_확장자_검증() {
        assertThatThrownBy(() -> new SessionImage("next.jjpg", 300, 200, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 확장자입니다.");
    }
    @Test
    void 강의_이미지_유무_검증() {
        assertThatThrownBy(() -> new SessionImage("next", 300, 200, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("확장자가 존재하지 않습니다.");
    }

}
