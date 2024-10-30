package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageTypeTest {

    @Test
    @DisplayName("성공 - ImageType 클래스가 확장자 대소문자를 구분하지 않는다.")
    void ignoreCaseTest() {
        assertThat(ImageType.of("SVG"))
            .isEqualTo(ImageType.of("svg"));
    }

    @Test
    @DisplayName("성공 - of 메서드가 올바른 문자열 확장자가 주어졌을 때 enum 인스턴스를 반환한다.")
    void ofTest() {
        assertThat(ImageType.of("svg"))
            .isEqualTo(ImageType.SVG);
    }

    @Test
    @DisplayName("실패 - of 메서드가 잘못된 문자열 확장자가 주어졌을 때 예외를 반환한다.")
    void throwExceptionWhenInvalidImageType() {
        assertThatThrownBy(() -> ImageType.of("exe"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("지원하지 않는 확장자 입니다.");
    }
}