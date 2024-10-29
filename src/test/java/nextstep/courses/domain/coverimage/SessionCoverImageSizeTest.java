package nextstep.courses.domain.coverimage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionCoverImageSizeTest {

    @Test
    void 너비_300_미만일_시_예외() {
        assertThatThrownBy(
                () -> new SessionCoverImageSize(299, 200)
        );
    }

    @Test
    void 높이_200_미만일_시_예외() {
        assertThatThrownBy(
                () -> new SessionCoverImageSize(300, 199)
        );
    }

    @Test
    void 비율이_300_200아닐_시_예외() {
        assertThatThrownBy(
                () -> new SessionCoverImageSize(400, 200)
        );
    }
}
