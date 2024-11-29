package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.NonPositiveException;
import org.junit.jupiter.api.Test;

public class CapacityTest {

    @Test
    void 용량이_음수_인경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Capacity(-1)).isInstanceOf(NonPositiveException.class);
    }
}
