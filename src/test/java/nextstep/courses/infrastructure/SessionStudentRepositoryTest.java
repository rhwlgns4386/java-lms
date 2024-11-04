package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
public class SessionStudentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaidSessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PaidSessionRepository paidSessionRepository;
    private CourseRepository courseRepository;
    private SessionStudentRepository sessionStudentRepository;

    @BeforeEach
    void setUp() {
        courseRepository = new JdbcCourseRepository(jdbcTemplate);
        paidSessionRepository = new JdbcPaidSessionRepository(jdbcTemplate);
        sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
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


        PaidSession savedSession = paidSessionRepository.findById(sessionId);
        savedSession.register(JAVAJIGI, new Payment(sessionId, JAVAJIGI.getId(), 20000L));

        sessionStudentRepository.registerStudentToSession(sessionId, JAVAJIGI);
        List<Long> registeredStudentIds = sessionStudentRepository.getRegisteredStudentIds(sessionId);
        assertThat(registeredStudentIds.get(0)).isEqualTo(JAVAJIGI.getId());

        LOGGER.debug("Saved Student: {}", registeredStudentIds);
    }
}
