package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoverImageTest {
    private static final int ONE_MEGA_BITE = 1024 * 1024;

    @Test
    @DisplayName("실패 - 파일 크기가 1MB 이상일 때 예외를 반환한다.")
    void throwExceptionWhenInvalidFileSize() {
        int invalidFileSize = ONE_MEGA_BITE + 1;
        ImageFile invalidSizeImageFile = new ImageFile(invalidFileSize, 300, 200, "jpg");

        assertThatThrownBy(() -> CoverImage.of(invalidSizeImageFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("강의 이미지 크기는 1MB 이하여야 합니다.");
    }

    @Test
    @DisplayName("실패 - 이미지 비율이 3:2가 아닐 때 예외를 반환한다.")
    void throwExceptionWhenInvalidImageRatio() {
        ImageFile invalidRatioImageFile = new ImageFile(ONE_MEGA_BITE, 300, 250, "jpg");

        assertThatThrownBy(() -> CoverImage.of(invalidRatioImageFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("강의 이미지 너비와 높이의 비율은 3:2여야 합니다.");
    }

    @Test
    @DisplayName("실패 - 이미지 너비가 300픽셀 미만일 때 예외를 반환한다.")
    void throwExceptionWhenInvalidWidth() {
        ImageFile invalidWidthImageFile = new ImageFile(ONE_MEGA_BITE, 299, 200, "jpg");

        assertThatThrownBy(() -> CoverImage.of(invalidWidthImageFile))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("강의 이미지 크기가 맞지 않습니다.(너비 300픽셀 이상, 높이 200픽셀 이상)");
    }

    @Test
    @DisplayName("실패 - 이미지 높이가 200픽셀 미만일 때 예외를 반환한다.")
    void throwExceptionWhenInvalidHeight() throws Exception {
        ImageFile invalidHeightImage = new ImageFile(ONE_MEGA_BITE, 300, 199, "jpg");

        assertThatThrownBy(() -> CoverImage.of(invalidHeightImage))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("강의 이미지 크기가 맞지 않습니다.(너비 300픽셀 이상, 높이 200픽셀 이상)");
    }

    @Test
    @DisplayName("성공 - 올바른 이미지 파일이 주어졌을 때 객체가 생성된다.")
    void extensionTest() {
        ImageFile validImageFile = new ImageFile(ONE_MEGA_BITE, 300, 200, "svg");

        assertThat(CoverImage.of(validImageFile))
            .isNotNull();
    }
}