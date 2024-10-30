package nextstep.courses.domain.image;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class FreeSessionCoverImageTest {

    @Test
    void 이미지_타입_제한_성공_테스트_코드() {
        SessionCoverImage sessionCoverImage = new SessionCoverImage(1L, 150, "leo.png", 300, 200);

        assertThat(sessionCoverImage).isEqualTo(new SessionCoverImage(1L, new CoverImageVolume(150), CoverImageExtensionType.valueOfExtension("leo.png"), new CoverImageFileSize(300, 200)));
    }


    @Test
    void 이미지_타입_제한_실패_테스트_코드() {
        assertThatThrownBy(() -> new SessionCoverImage(1L, 150, "leo.pdf", 300, 200))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 이미지_크기_최소_크기_실패_테스트() {
        assertThatThrownBy(() -> new SessionCoverImage(1L, 150, "leo.png", 300, 150))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("잘못된 크기입니다: 높이는 300픽셀, 넓이는 200픽셀 이상이어야 합니다.");
    }

    @Test
    void 이미지_크기_사이즈_비율_실패_테스트() {
        assertThatThrownBy(() -> new SessionCoverImage(1L, 150, "leo.png", 300, 300))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("잘못된 비율입니다: 비율은 반드시 3:2여야 합니다.");
    }

}
