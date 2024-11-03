package nextstep.courses.infrastructure.course;

import nextstep.courses.entity.CourseEntity;
import nextstep.courses.infrastructure.cover.JdbcCoverImageRepository;
import nextstep.courses.infrastructure.session.JdbcSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class CourseRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository = new JdbcCourseRepository(jdbcTemplate,
                new JdbcSessionRepository(jdbcTemplate, new JdbcCoverImageRepository(jdbcTemplate)));
    }

    @Test
    void crud() {
        CourseEntity course = new CourseEntity("TDD, 클린 코드 with Java", 1L, LocalDateTime.now());
        int count = courseRepository.save(course);
        assertThat(count).isEqualTo(1);
        CourseEntity savedCourse = courseRepository.findById(2L);
        assertThat(course.getTitle()).isEqualTo(savedCourse.getTitle());
        LOGGER.debug("Course: {}", savedCourse);
    }
}
