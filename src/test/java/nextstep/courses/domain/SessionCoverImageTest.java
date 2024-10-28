package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionCoverImageTest {

    @Test
    void 유효한_이미지_파일() {
        assertDoesNotThrow(() -> new SessionCoverImage(500 * 1024, "jpg", 300, 200));
    }

    @Test
    void 파일_크기가_1MB_초과할_경우_예외_발생() {
        assertThrows(IllegalArgumentException.class, () -> new SessionCoverImage(2 * 1024 * 1024, "jpg", 300, 200));
    }

    @Test
    void 허용되지_않은_파일_형식일_경우_예외_발생() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SessionCoverImage(500 * 1024, "txt", 300, 200));
        assertEquals("Invalid file type: txt", exception.getMessage());
    }

    @Test
    void 너비_300픽셀_미만이고_높이_200픽셀_미만인_경우_예외_발생() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SessionCoverImage(500 * 1024, "jpg", 200, 100));
        assertEquals("Invalid image dimensions: 200x100", exception.getMessage());
    }

    @Test
    @DisplayName("비율이 3:2가 아닌 경우 예외 발생")
    void invalidAspectRatioFileShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SessionCoverImage(500 * 1024, "jpg", 300, 250));
        assertEquals("Invalid aspect ratio: 300x250", exception.getMessage());
    }
}
