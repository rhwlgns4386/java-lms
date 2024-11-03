package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionImageTest {

    @Test
    @DisplayName("이미지 크기는 1MB 이하_이미지 타입은 특정 확장자_이미지 가로는 300 세로는 200 픽셀 이상_비율은 3:2 pass")
    void validationSessionImage() {
        SessionImage PASS_IMAGE = new SessionImage(100, "jpg", 300, 200, "image");
    }

    @Test
    @DisplayName("이미지 크기는 1MB 이상인경우 오류")
    void validateImageSize_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            SessionImage sessionImage = new SessionImage(100000000, "jpg", 300, 200, "image");
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("이미지 크기는 1MB이하여야 합니다.");
    }

    @Test
    @DisplayName("이미지 타입은 특정 확장자가 아닌 경우 오류")
    void validateImageType_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            SessionImage sessionImage2 = new SessionImage(100, "txt", 300, 200, "image");
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("이미지 타입은 gif, jpg(jpeg 포함),png, svg만 허용합니다.");
    }

    @Test
    @DisplayName("이미지 가로는 300 세로는 200 픽셀 이상이 아니거나 비율이 3:2가 아니면 오류")
    void validateRatio_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            SessionImage sessionImage = new SessionImage(100, "jpg", 700, 400, "image");
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("width와 height의 비율은 3:2여야 한다.");

        assertThatThrownBy(() -> {
            SessionImage sessionImage = new SessionImage(100, "jpg", 200, 300, "image");
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
    }

}
