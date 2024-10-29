package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenerationTest {

    @DisplayName("기수는 0이하가 될 수 없다.")
    @Test
    void invalidGeneration() {
        assertThatThrownBy(() -> new Generation(0))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
