package nextstep.courses.domain.session.sessionCoverImage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageTypeTest {

    @Test
    @DisplayName("허용되지 않은 이미지 파일 유형일 경우 IllegalArgumentException이 발생한다.")
    void is_valid_하용되지_않은_파일유형() {
        Assertions.assertThatThrownBy(() -> {
            ImageType.isValid("txt");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("허용되지 않은 파일유형일 경우 false를 반환한다.")
    void has_type_허용되지_않은_파일유형() {
        boolean hasType = ImageType.hasType("pptx");
        Assertions.assertThat(hasType).isFalse();
    }
}
