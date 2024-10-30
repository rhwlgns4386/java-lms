package nextstep.sessions.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageHeightTest {

    @Test
    @DisplayName("이미지 가로 최소픽셀 미달")
    void min_height() {
        assertThatThrownBy(()-> new ImageHeight(199)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지 가로 필셀 적합")
    void normal_height() {

        assertThat(new ImageHeight(400).getProportionHeight()).isEqualTo(1200);
    }
}
