package nextstep.courses.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
public class CourseSessionRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseSessionRepositoryTest.class);

    @Autowired
    private JdbcOperations jdbcTemplate;

    private CourseSessionRepository courseSessionRepository;

    @BeforeEach
    void setUp() {
        this.courseSessionRepository = new JdbcCourseSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        long courseId = 1L;
        List<Long> sessionIds = List.of(1L, 2L);
        int count = courseSessionRepository.save(courseId, sessionIds);
        List<Long> findSessionIds = courseSessionRepository.findByCourseId(1L);
        assertAll(
                () -> assertThat(count).isEqualTo(2),
                () -> assertThat(findSessionIds).isEqualTo(sessionIds)
        );
        LOGGER.debug("Sessions: {}", findSessionIds);
    }
}
