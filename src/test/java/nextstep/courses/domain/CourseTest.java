package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CourseTest {
    @Test
    @DisplayName("Course class 생성")
    void createCourseTest() {
        Long id = 1L;
        Long order = 1L;
        String title = "TDD with Java";
        LocalDate startDate = LocalDate.of(2024, 9, 23);
        LocalDate endDate = LocalDate.of(2024, 11, 7);
        Long creatorId = 1L;


        Course course = new Course(id, order, title, startDate, endDate, creatorId);

        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course.getId()).isEqualTo(id);
    }
}
