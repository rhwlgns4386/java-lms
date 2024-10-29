package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ImageWidthTest {

    @Test
    @DisplayName("이미지 가로 최소픽셀 미달")
    void min_width() {
        assertThatThrownBy(()-> new ImageWidth(299)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지 가로 필셀 적합")
    void normal_width() {
        assertThat(new ImageWidth(400).getProportionWidth()).isEqualTo(800);
    }
}
