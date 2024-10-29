package nextstep.courses.domain;

import nextstep.courses.domain.Image.ImageType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ImageTypeTest {
    @Test
    void 존재하는_이미지_타입_확인() {
        assertTrue(ImageType.contains(ImageType.PNG));
        assertTrue(ImageType.contains(ImageType.GIF));
        assertTrue(ImageType.contains(ImageType.JPEG));
        assertTrue(ImageType.contains(ImageType.JPG));
        assertTrue(ImageType.contains(ImageType.SVG));
    }

    @Test
    void 존재하지_않는_이미지_타입_확인() {
        assertFalse(ImageType.contains("BMP"));
    }
}
