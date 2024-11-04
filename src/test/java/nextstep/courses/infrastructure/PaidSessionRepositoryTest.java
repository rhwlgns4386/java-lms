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
public class PaidSessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaidSessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PaidSessionRepository paidSessionRepository;
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository = new JdbcCourseRepository(jdbcTemplate);

        paidSessionRepository = new JdbcPaidSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Course course = new Course("TDD, 클린 코드 with Java", 1L);
        courseRepository.save(course);

        PaidSession paidSession = new PaidSession(
                "자바",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31),
                SessionStatus.OPEN,
                50,
                20000L,
                new SessionImage(500, 300, 200, "jpg")
        );

        Long courseId = 1L;
        Long sessionId = paidSessionRepository.saveBaseSession(paidSession, courseId);

        System.out.println(sessionId);
        assertThat(sessionId).isNotNull();

        PaidSession savedSession = paidSessionRepository.findById(sessionId);
        assertThat(savedSession.getTitle()).isEqualTo(paidSession.getTitle());
        assertThat(savedSession.getSessionStatus()).isEqualTo(paidSession.getSessionStatus());
        assertThat(savedSession.getMaxCapacity()).isEqualTo(paidSession.getMaxCapacity());
        assertThat(savedSession.getFee()).isEqualTo(paidSession.getFee());
        assertThat(savedSession.getImageSize()).isEqualTo(paidSession.getImageSize());

        LOGGER.debug("Saved PaidSession: {}", savedSession);
    }
}
