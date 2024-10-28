package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    void 생성() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 12);

        Course course = new Course(
            "TDD",
            1L,
            19,
            start,
            end
        );

        assertThat(course).isEqualTo(new Course("TDD", 1L, 19, start, end));
    }
}
