package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.InvalidIsolationLevelException;

import java.awt.*;

import static org.assertj.core.api.Assertions.*;

public class SessionImagePixelTest {
    @Test
    @DisplayName("이미지크기_가로세로_비례검증")
    void validate_widthAndHeightPicxel() {
        ImageWidth width = new ImageWidth(300);
        ImageHeight height = new ImageHeight(400);
        assertThatThrownBy(() -> new SessionImagePixel(width, height)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지크기_가로세로_정상비율")
    void validate_widthAndHeightPicxel_normal() {
        ImageWidth width = new ImageWidth(300);
        ImageHeight height = new ImageHeight(200);
        assertThat(new SessionImagePixel(width, height));
    }
}
