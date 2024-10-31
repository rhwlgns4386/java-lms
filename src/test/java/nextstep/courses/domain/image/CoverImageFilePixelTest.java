package nextstep.courses.domain.image;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CoverImageFilePixelTest {

    @Test
    void 이미지_사이즈_저장_성공_테스트() {
        CoverImageFileSize coverImageFileSize = new CoverImageFileSize(300,200);

        assertThat(coverImageFileSize).isEqualTo(new CoverImageFileSize(300,200));
    }

    @Test
    void 이미지_사이즈_저장_최소_크기_실패_테스트() {
        assertThatThrownBy(() -> new CoverImageFileSize(300,150))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("잘못된 크기입니다: 높이는 300픽셀, 넓이는 200픽셀 이상이어야 합니다.");

    }

    @Test
    void 이미지_사이즈_저장_비율_실패_테스트() {
        assertThatThrownBy(() -> new CoverImageFileSize(300,400))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("잘못된 비율입니다: 비율은 반드시 3:2여야 합니다.");
    }

}
