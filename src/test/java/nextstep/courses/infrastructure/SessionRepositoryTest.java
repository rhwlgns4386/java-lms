package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImagePixel;
import nextstep.courses.domain.image.ImageSize;
import nextstep.courses.domain.image.ImageType;
import nextstep.courses.domain.session.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(namedParameterJdbcTemplate);
    }

    @Order(1)
    @Test
    void crud() {
        Long courseId = 1L;
        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImagePixel(300, 200));
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);
        String title = "TDD";

        PaidSession paidSession = new PaidSession(title, image, sessionDate, new SessionCapacity(10), new Money(200_000L));
        long id1 = sessionRepository.save(paidSession, courseId);
        Assertions.assertThat(id1).isEqualTo(1);

        FreeSession freeSession = new FreeSession(title, sessionDate, image);
        long id2 = sessionRepository.save(freeSession, courseId);
        Assertions.assertThat(id2).isEqualTo(2);

        Session savePaidSession = sessionRepository.findById(1L);
        Assertions.assertThat(savePaidSession.getSessionType()).isEqualTo(SessionType.PAID);
        Session saveFreeSession = sessionRepository.findById(2L);
        Assertions.assertThat(saveFreeSession.getSessionType()).isEqualTo(SessionType.FREE);
    }

    @Order(2)
    @Test
    void findAllByCourseIdTest() {
        Long courseId = 1L;
        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImagePixel(300, 200));
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);
        String title = "TDD";

        PaidSession paidSession = new PaidSession(title, image, sessionDate, new SessionCapacity(10), new Money(200_000L));
        sessionRepository.save(paidSession, courseId);

        FreeSession freeSession = new FreeSession(title, sessionDate, image);
        sessionRepository.save(freeSession, courseId);

        List<Session> sessions = sessionRepository.findAllByCourseId(courseId);

        Assertions.assertThat(sessions).hasSize(2);
    }
}
