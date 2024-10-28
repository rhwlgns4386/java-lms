package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class FreeCourseTest {

    @DisplayName("무료강의는 최대 수강 인원 제한이 없다.")
    @Test
    void register() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        FreeCourse freeCourse = new FreeCourse(CourseStatus.OPEN, new Course2Period(startDate, endDate));
        Money paidAmount = new Money(0);

        assertDoesNotThrow(() -> freeCourse.register(paidAmount));
    }
}
