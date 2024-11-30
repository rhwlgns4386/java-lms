package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.NonPositiveException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CapacityTest {

    @Test
    void 용량이_음수_인경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Capacity(-1)).isInstanceOf(NonPositiveException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"10,false", "9,true"})
    void 용량보다_넘어가는지_확인한다(int size, boolean result) {
        Capacity capacity = new Capacity(10);
        assertThat(capacity.canEnroll(size)).isEqualTo(result);
    }
}
