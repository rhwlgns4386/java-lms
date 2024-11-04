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
class SessionRepositoryTest {
    public static final SessionPeriod PERIOD = new SessionPeriod(LocalDate.now(), LocalDate.now().plusDays(1L));
    public static final SessionCoverImage COVER_IMAGE = new SessionCoverImage(500 * 1024, "jpg", 300, 200);
    public static final SessionAmount AMOUNT = new SessionAmount(100_000L);
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Session session = Session.paidSession(0L, 0L, PERIOD, COVER_IMAGE, AMOUNT, 1, SessionStatus.RECRUITING);
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
        Session savedSession = sessionRepository.findById(1L);
        assertThat(savedSession.getCoverImage()).isEqualTo(COVER_IMAGE);
        LOGGER.debug("Session: {}", savedSession);
    }
}