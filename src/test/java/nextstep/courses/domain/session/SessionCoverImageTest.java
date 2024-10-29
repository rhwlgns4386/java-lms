package nextstep.courses.domain.session;

import nextstep.courses.domain.image.CoverImageFileSize;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionCoverImageTest {

    @Test
    void 이미지_타입_제한_성공_테스트_코드(){
        SessionCoverImage sessionCoverImage = new SessionCoverImage(150, "leo.png");

        Assertions.assertThat(sessionCoverImage).isEqualTo(new SessionCoverImage(new CoverImageFileSize(150), "leo.png"));
    }


    @Test
    void 이미지_타입_제한_실패_테스트_코드() {
        Assertions.assertThatThrownBy(() -> new SessionCoverImage(150, "leo.pdf"))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
