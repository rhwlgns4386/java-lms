package nextstep.courses.domain.coverimage;

import nextstep.courses.CoverImageException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionCoverImagePathTest {

    @Test
    void 파일_명_특수문자_있을_시_예외() {
        assertThatThrownBy(
                () -> new SessionCoverImagePath("/", "?파일이다.jpg")
        ).isInstanceOf(CoverImageException.class);
    }

}
