package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Course2Test {
    @DisplayName("강의상태가 모집 중이아니면 수강신청은 예외로 처리한다.")
    @Test
    void register() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        Course2 firstCourse = new Course2(CourseStatus.READY, new Course2Period(startDate,endDate));
        assertThatThrownBy(firstCourse::register)
                .isInstanceOf(IllegalArgumentException.class);

        Course2 secondCourse = new Course2(CourseStatus.CLOSED, new Course2Period(startDate,endDate));
        assertThatThrownBy(secondCourse::register)
                .isInstanceOf(IllegalArgumentException.class);

    }
}
