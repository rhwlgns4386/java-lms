package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class FreeSessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaidSessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private FreeSessionRepository freeSessionRepository;
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository = new JdbcCourseRepository(jdbcTemplate);

        freeSessionRepository = new JdbcFreeSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Course course = new Course("TDD, 클린 코드 with Java", 1L);
        courseRepository.save(course);

        FreeSession freeSession = new FreeSession(
                "자바",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31),
                new SessionImage(500, 300, 200, "jpg")
        );

        Long courseId = 1L;
        Long sessionId = freeSessionRepository.saveBaseSession(freeSession, courseId);

        System.out.println(sessionId);
        assertThat(sessionId).isNotNull();

        FreeSession savedSession = freeSessionRepository.findById(sessionId);
        assertThat(savedSession.getTitle()).isEqualTo(freeSession.getTitle());
        assertThat(savedSession.getSessionStatus()).isEqualTo(freeSession.getSessionStatus());
        assertThat(savedSession.getImageSize()).isEqualTo(freeSession.getImageSize());

        LOGGER.debug("Saved PaidSession: {}", savedSession);
    }
}
