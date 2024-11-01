package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.FreeSession;
import nextstep.fixture.FreeSessionCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class CourseRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private JdbcOperations jdbcTemplate;

    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        CourseSessionRepository courseSessionRepository = new JdbcCourseSessionRepository(jdbcTemplate);
        SessionImageRepository sessionImageRepository = new JdbcSessionImageRepository(jdbcTemplate);
        SessionStudentRepository sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
        SessionRepository sessionRepository = new JdbcSessionRepository(jdbcTemplate, sessionImageRepository, sessionStudentRepository);
        courseRepository = new JdbcCourseRepository(jdbcTemplate, courseSessionRepository, sessionRepository);
    }

    @Test
    void crud() {
        FreeSession session = FreeSessionCreator.standard();
        Course course = new Course("TDD, 클린 코드 with Java", 1L, List.of(session));
        int count = courseRepository.save(course);
        Course savedCourse = courseRepository.findById(1L);
        Assertions.assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(course.getTitle()).isEqualTo(savedCourse.getTitle()),
                () -> assertThat(course.getSessions()).isEqualTo(List.of(session))
        );
        LOGGER.debug("Course: {}", savedCourse);
    }
}
