package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CourseTest {
    @Test
    public void 코스는_기수를_가진다() {
        Course course = new Course("자바지기", 1L, 1);

        assertThat(course.getCohort()).isEqualTo(1);
    }
}
