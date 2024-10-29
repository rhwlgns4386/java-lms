package nextstep.courses.domain.image;

import nextstep.courses.domain.image.CoverImageFileSize;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CoverImageFileSizeTest {

    @Test
    void 이미지_타입_1MB_이하_성공_테스트() {
        CoverImageFileSize sessionCoverImage = new CoverImageFileSize(1500);

        assertThat(sessionCoverImage).isEqualTo(new CoverImageFileSize(1500));
    }

    @Test
    void 이미지_타입_1MB_이하_실패_테스트() {
        assertThatThrownBy(() -> new CoverImageFileSize(10_500_000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("10MB 파일 크기를 초과하였습니다.");
    }

}
