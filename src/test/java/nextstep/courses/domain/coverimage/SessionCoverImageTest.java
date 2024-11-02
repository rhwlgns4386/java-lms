package nextstep.courses.domain.coverimage;

import nextstep.courses.CoverImageException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionCoverImageTest {
    private static final SessionCoverImageSize SIZE = new SessionCoverImageSize(300, 200);
    private static final SessionCoverImagePath PATH = new SessionCoverImagePath("/", "example.jpg");

    public static final SessionCoverImage IMAGE = new SessionCoverImage(1L, 1 * 1024 * 1024L, PATH, SIZE);

    @Test
    void 사이즈_1MB_초과_예외() {
        SessionCoverImagePath imagePath = SessionCoverImagePath.create("/", "example.jpg");
        SessionCoverImageSize imageSize = new SessionCoverImageSize(300, 200);
        long storageCapacity = 2 * 1024 * 1024;

        assertThatThrownBy(
                () -> SessionCoverImage.create(1L, storageCapacity, imagePath, imageSize)
        ).isInstanceOf(CoverImageException.class);
    }

}
