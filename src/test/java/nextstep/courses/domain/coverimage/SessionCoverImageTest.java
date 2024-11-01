package nextstep.courses.domain.coverimage;

import nextstep.courses.CoverImageException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionCoverImageTest {
    private static final SessionCoverImageSize SIZE = new SessionCoverImageSize(300, 200);
    private static final SessionCoverImagePath PATH = new SessionCoverImagePath("/", "example.jpg");

    public static final SessionCoverImage IMAGE = new SessionCoverImage(1 * 1024 * 1024, PATH, SIZE);

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
