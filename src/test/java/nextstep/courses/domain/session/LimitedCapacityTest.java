package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LimitedCapacityTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("수강신청 인원이 0이하일 경우 예외가 발생한다.")
    void throwExceptionWhenCapacityLessThanZero(int capacity) {
        assertThatThrownBy(() -> new LimitedCapacityPolicy(capacity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수강 인원은 1명 이상이어야 합니다.");
    }
}