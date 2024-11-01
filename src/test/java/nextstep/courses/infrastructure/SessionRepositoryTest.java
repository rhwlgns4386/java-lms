package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Long courseId = 1L;
        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImagePixel(300L, 200L));
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);
        String title = "TDD";

        PaidSession paidSession = new PaidSession(title, image, sessionDate, new SessionCapacity(10), new Money(200_000L));
        int count1 = sessionRepository.save(paidSession, courseId);
        Assertions.assertThat(count1).isEqualTo(1);

        FreeSession freeSession = new FreeSession(title, sessionDate, image);
        int count2 = sessionRepository.save(freeSession, courseId);
        Assertions.assertThat(count2).isEqualTo(1);

        Session savePaidSession = sessionRepository.findById(1L);
        Assertions.assertThat(savePaidSession.getSessionType()).isEqualTo(SessionType.PAID);
        Session saveFreeSession = sessionRepository.findById(2L);
        Assertions.assertThat(saveFreeSession.getSessionType()).isEqualTo(SessionType.FREE);
    }

}
