package nextstep.courses.domain.image;

import nextstep.courses.domain.Image.ImageSize;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageSizeTest {

    @Test
    public void 이미지_크기_정상_생성_테스트() {
        ImageSize imageSize = new ImageSize(1);
        assertThat(imageSize.getSize()).isEqualTo(1);
    }

    @Test
    public void 이미지_크기_최대값_초과시_예외_테스트() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ImageSize(2);
        });
        assertThat(exception.getMessage()).isEqualTo("이미지의 크기는 1보다 클 수 없습니다.");
    }

    @Test
    public void 이미지_크기_최대값_테스트() {
        ImageSize imageSize = new ImageSize(1);
        assertThat(imageSize.getSize()).isEqualTo(1);
    }
}
