package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.*;

public class SessionImageTest {
    @Test
    @DisplayName("이미지 사이즈 정상")
    void validate_image_size_normal() {
        assertThatCode(()->new SessionImage("abc", new ImageSize(1024 * 1024),  SessionImageTypeEnum.GIF, new SessionImagePixel(new ImageWidth(300), new ImageHeight(200))))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이미지 최대 사이즈 초과")
    void validate_image_size_over() {
        assertThatThrownBy(()->new SessionImage("abc", new ImageSize(1025 * 1029), SessionImageTypeEnum.GIF, new SessionImagePixel(new ImageWidth(300), new ImageHeight(200))))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("이미지 사이즈 값 누락")
    void validate_image_size_null() {
        assertThatThrownBy(()->new SessionImage("abc", null, SessionImageTypeEnum.GIF, new SessionImagePixel(new ImageWidth(300), new ImageHeight(200))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지 타입 값 누락")
    void validate_image_type_null() {
        assertThatThrownBy(()->new SessionImage("abc", new ImageSize(1024 * 1024), null, new SessionImagePixel(new ImageWidth(300), new ImageHeight(200))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지 픽셀 값 누락")
    void validate_image_pixel_null() {
        assertThatThrownBy(()->new SessionImage("abc", new ImageSize(1024 * 1024), SessionImageTypeEnum.JPG, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
