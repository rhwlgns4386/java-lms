package nextstep.sessions.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class CoverImageTest {
    public static final CoverImage IMAGE = new CoverImage(1L, 1024*1024, ImageType.GIF, 300, 200);

    @Test
    void 이미지_생성성공() {
        assertThatNoException().isThrownBy(() ->
                new CoverImage(1L, 1024*1024, ImageType.GIF, 300, 200));
    }

    @Test
    void 이미지_생성실패_사이즈_초과() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new CoverImage(1L, 1024*1024 + 1, ImageType.GIF, 300, 200));
    }

    @Test
    void 이미지_생성실패_비율_안맞음() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new CoverImage(1L, 1024*1024, ImageType.GIF, 200, 300));
    }
}
