package nextstep.courses.domain.image;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CoverImageVolumeTest {

    @Test
    void 이미지_타입_1MB_이하_성공_테스트() {
        CoverImageVolume sessionCoverImage = new CoverImageVolume(1000);

        assertThat(sessionCoverImage).isEqualTo(new CoverImageVolume(1000));
    }

    @Test
    void 이미지_타입_1MB_이하_실패_테스트() {
        assertThatThrownBy(() -> new CoverImageVolume(10_500_000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("1MB 파일 크기를 초과하였습니다.");
    }

}
