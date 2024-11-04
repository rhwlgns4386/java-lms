package nextstep.images.domain;

import nextstep.sessions.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ImageTest {
    @DisplayName("이미지 크기가 1MB 초과할 시 IllegalArgumentException 예외가 발생한다.")
    @Test
    void create_fail_when_size_is_under_1MB() {
        int given = 1001;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Image(given, "png", 300, 200, LocalDateTime.now()))
                .withMessage("이미지 크기는 1MB 이하여야 합니다.");
    }

    @DisplayName("타입이 gif, jpg(jpeg), png, svg가 아닐 경우 IllegalArgumentException 예외가 발생한다.")
    @Test
    void create_fail_when_type_is_not_allowed() {
        String given = "pdf";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Image(1000, given, 300, 200, LocalDateTime.now()))
                .withMessage("'pdf'는 허용하지 않는 이미지 타입입니다.");
    }

    @DisplayName("width 혹은 height가 200픽셀 미만일 경우 IllegalArgumentException 예외가 발생한다.")
    @Test
    void create_fail_when_width_or_height_is_lower_than_200Pixels() {
        int given = 298;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Image(1000, "png", given, 199, LocalDateTime.now()))
                .withMessage("width와 height는 200 픽셀 이상이어야 합니다.(width: 298px, height: 199px)");
    }

    @DisplayName("width와 height의 비율이 3:2가 아닐 경우 IllegalArgumentException 예외가 발생한다.")
    @Test
    void create_fail_when_width_to_height_ratio_is_not_allowed() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Image(1000, "png", 600, 401, LocalDateTime.now()))
                .withMessage("width와 height의 비율은 3:2여야 합니다.(width: 600px, height: 401px)");
    }

    @DisplayName("이미지 크기는 1MB 이하로 생성한다.")
    @Test
    void create_success() {
        LocalDateTime createdAt = LocalDateTime.now();

        Image image = new Image(1000, "jpeg", 600, 400, createdAt);

        assertThat(image).isEqualTo(new Image(1000, "jpeg", 600, 400, createdAt));
    }
}
