package nextstep.courses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SessionImageTest {

    private Size size;

    @BeforeEach
    void setUp() {
        size = new Size(300, 200);
    }

    @DisplayName("이미지 파일 크기가 1MB 이하여야 한다")
    @Test
    void limitFileSize() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SessionImage(10000L,"/test.jpg", 1_048_577, size, 1L, LocalDateTime.now(), LocalDateTime.now()))
                .withMessage("이미지 크기가 1MB 이하여야 합니다");
    }

    @DisplayName("확장자가 없는 이미지 파일은 등록할 수 없다")
    @Test
    void validateFilePath(){
        String filePath = "확장자없는파일경로";
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SessionImage(10000L, filePath, 1_048_576, size, 1L, LocalDateTime.now(), LocalDateTime.now()))
                .withMessage("유효하지 않은 파일 경로입니다");
    }
}
