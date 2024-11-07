package nextstep.courses.domain.image;

import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.image.SessionCoverImage.SessionCoverImageBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class SessionCoverImageTest {

    @Test
    void 이미지_타입_제한_성공_테스트_코드() {
        SessionCoverImage sessionCoverImage = new SessionCoverImage.SessionCoverImageBuilder().id(1L).fileName("leo.png").filePath("/home/lms/image/cover/leo.png").volume(150).width(200).height(300).build();

        assertThat(sessionCoverImage).isEqualTo(new SessionCoverImage(1L, 1L, "leo.png", new CoverImageVolume(150), CoverImageExtensionType.valueOfExtension("leo.png"), new CoverImageFileSize(200, 300), "/home/lms/image/cover/leo.png"));
    }


    @Test
    void 이미지_타입_제한_실패_테스트_코드() {
        assertThatThrownBy(() -> new SessionCoverImageBuilder().id(1L).sessionId(1L).fileName("leo.pdf").volume(150).width(200).height(300).build()).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 이미지_크기_최소_크기_실패_테스트() {

        assertThatThrownBy(() -> new SessionCoverImageBuilder().id(1L).sessionId(1L).fileName("leo.png").volume(150).width(300).height(150).build()).isInstanceOf(IllegalArgumentException.class).hasMessageMatching("잘못된 크기입니다: 높이는 300픽셀, 넓이는 200픽셀 이상이어야 합니다.");
    }

    @Test
    void 이미지_크기_사이즈_비율_실패_테스트() {
        assertThatThrownBy(() -> new SessionCoverImageBuilder().id(1L).sessionId(1L).fileName("leo.png").volume(150).width(300).height(300).build()).isInstanceOf(IllegalArgumentException.class).hasMessageMatching("잘못된 비율입니다: 비율은 반드시 3:2여야 합니다.");
    }

}
