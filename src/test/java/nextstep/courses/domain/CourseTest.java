package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CourseTest {
    @Test
    @DisplayName("Course class 생성")
    void createCourseTest() {
        Long id = 1L;
        Long order = 1L;
        String title = "TDD with Java";
        Long creatorId = 1L;


        Course course = new Course(id, order, title, creatorId);

        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course.getId()).isEqualTo(id);
    }

}
