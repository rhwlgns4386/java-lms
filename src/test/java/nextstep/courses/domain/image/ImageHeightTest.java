package nextstep.courses.domain.image;

import nextstep.courses.domain.Image.ImageHeight;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageHeightTest {

    @Test
    public void 이미지_높이_정상_생성_테스트() {
        ImageHeight imageHeight = new ImageHeight(300);
        assertThat(imageHeight.getHeight()).isEqualTo(300);
    }

    @Test
    public void 이미지_높이_최소값_이하_예외_테스트() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ImageHeight(150);
        });
        assertThat(exception.getMessage()).isEqualTo("이미지의 높이는 200보다 작을 수 없습니다.");
    }

    @Test
    public void 이미지_높이_최소값_테스트() {
        ImageHeight imageHeight = new ImageHeight(200);
        assertThat(imageHeight.getHeight()).isEqualTo(200);
    }
}
