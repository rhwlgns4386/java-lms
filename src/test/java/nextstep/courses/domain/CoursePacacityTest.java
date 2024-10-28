package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoursePacacityTest {

    @DisplayName("최대 수강인원이 0명이면 예외로 처리한다.")
    @Test
    void invalidMaxCapacity() {
        assertThatThrownBy(() -> new CourseCapacity(1, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("현재 수강인원이 0보다 작으면 예외로 처리한다.")
    @Test
    void invalidCurrentCapacity() {
        assertThatThrownBy(() -> new CourseCapacity(-1, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("현재 수강인원이 최대 수강인원을 초과하면 예외로 처리한다.")
    @Test
    void currentCapacityOverMaxCapacity() {
        assertThatThrownBy(() -> new CourseCapacity(2, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
