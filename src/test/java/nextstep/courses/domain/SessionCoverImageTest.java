package nextstep.courses.domain;

import nextstep.courses.domain.session.SessionCoverImage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SessionCoverImageTest {

    @Test
    void 이미지_타입_1MB_이하_성공_테스트() {
        SessionCoverImage sessionCoverImage = new SessionCoverImage(1500);

        assertThat(sessionCoverImage).isEqualTo(new SessionCoverImage(1500));
    }

    @Test
    void 이미지_타입_1MB_이하_실패_테스트() {
        assertThatThrownBy(() -> new SessionCoverImage(10_500_000)).isInstanceOf(IllegalArgumentException.class);
    }
}
