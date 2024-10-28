package nextstep.courses.domain.coverimage;

import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.courses.domain.coverimage.SessionCoverImagePath;
import nextstep.courses.domain.coverimage.SessionCoverImageSize;
import nextstep.qna.CoverImageException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionCoverImageTest {

    @Test
    void 사이즈_1MB_초과_예외() {
        SessionCoverImagePath imagePath = new SessionCoverImagePath("/", "example.jpg");
        SessionCoverImageSize imageSize = new SessionCoverImageSize(300, 200);
        long storageCapacity = 2 * 1024 * 1024;

        assertThatThrownBy(
                () -> new SessionCoverImage(storageCapacity, imagePath, imageSize)
        ).isInstanceOf(CoverImageException.class);
    }

}
