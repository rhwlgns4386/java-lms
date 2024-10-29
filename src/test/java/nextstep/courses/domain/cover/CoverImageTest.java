package nextstep.courses.domain.cover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CoverImageTest {

    @DisplayName("크기, 타입, 사이즈를 가지는 커버 이미지 객체를 만들 수 있다.")
    @Test
    void create() {
        CoverImage coverImage = new CoverImage(new CoverImageFile(30), CoverImageType.GIF, new CoverImageSize(300, 200));

        assertThat(coverImage).isNotNull();
    }
}
