package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StudentCapacityTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("수강신청 인원이 0이하일 경우 예외가 발생한다.")
    void throwExceptionWhenCapacityLessThanZero(int capacity) {
        assertThatThrownBy(() -> new StudentCapacity(capacity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수강 인원은 1명 이상이어야 합니다.");
    }

    @Test
    @DisplayName("수강신청 인원이 초과할 경우 예외가 발생한다.")
    void throwExceptionWhenCountThanCapacity() {
        StudentCapacity studentCapacity = new StudentCapacity(1);
        studentCapacity.increment();
        assertThatThrownBy(() -> studentCapacity.increment())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("정원을 초과했습니다.");
    }
}