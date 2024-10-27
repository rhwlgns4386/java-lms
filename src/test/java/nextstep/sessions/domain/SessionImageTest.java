package nextstep.sessions.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class SessionImageTest {
    public static final SessionImage IMAGE = new SessionImage("image_url", 1024*1024, ImageType.GIF, 300, 200);

    @Test
    void 이미지_생성성공() {
        assertThatNoException().isThrownBy(() ->
                new SessionImage("image_url", 1024*1024, ImageType.GIF, 300, 200));
    }

    @Test
    void 이미지_생성실패_사이즈_초과() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new SessionImage("image_url", 1024*1024 + 1, ImageType.GIF, 300, 200));
    }

    @Test
    void 이미지_생성실패_비율_안맞음() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new SessionImage("image_url", 1024*1024, ImageType.GIF, 200, 300));
    }
}
