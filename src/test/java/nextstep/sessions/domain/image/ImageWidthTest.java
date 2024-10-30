package nextstep.sessions.domain.image;

import nextstep.sessions.Image.ImageWidth;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageWidthTest {

    @Test
    public void 이미지_너비_정상_생성_테스트() {
        ImageWidth imageWidth = new ImageWidth(400);
        assertThat(imageWidth.getWidth()).isEqualTo(400);
    }

    @Test
    public void 이미지_너비_최소값_이하_예외_테스트() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ImageWidth(250);
        });
        assertThat(exception.getMessage()).isEqualTo("이미지의 너비는 300보다 작을 수 없습니다.");
    }

    @Test
    public void 이미지_너비_최소값_테스트() {
        ImageWidth imageWidth = new ImageWidth(300);
        assertThat(imageWidth.getWidth()).isEqualTo(300);
    }
}
