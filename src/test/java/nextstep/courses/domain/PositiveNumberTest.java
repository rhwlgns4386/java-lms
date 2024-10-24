package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositiveNumberTest {

    @Test
    @DisplayName("숫자를 증가시킨다.")
    void 숫자_증가() {
        PositiveNumber num = new PositiveNumber(4);
        assertThat(num.increase()).isEqualTo(new PositiveNumber(5));
    }

    @Test
    @DisplayName("음수일 경우 예외를 발생시킨다.")
    void 음수_예외() {
        assertThatThrownBy(() -> new PositiveNumber(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
