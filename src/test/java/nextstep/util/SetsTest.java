package nextstep.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;

class SetsTest {

    @Test
    void 오른쪽Set에_존재하지_않는_아이템들을_반환한다() {
        Set<Integer> left = Set.of(1, 2, 3);
        Set<Integer> right = Set.of(1, 2, 4);

        assertThat(Sets.difference(left, right)).isEqualTo(Set.of(3));
    }
}
