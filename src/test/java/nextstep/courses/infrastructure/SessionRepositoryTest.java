package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {
    public static final SessionPeriod PERIOD = new SessionPeriod(LocalDate.now(), LocalDate.now().plusDays(1L));
    public static final SessionAmount AMOUNT = new SessionAmount(100_000L);
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(namedParameterJdbcTemplate);
    }

    @Test
    void crud() {
        Session session = Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PROGRESSING, SessionRecruitment.RECRUITING);
        long sessionId = sessionRepository.save(session);
        assertThat(sessionId).isEqualTo(1);
        Session savedSession = sessionRepository.findById(1L).get();
        assertThat(savedSession.getPeriod()).isEqualTo(PERIOD);
        LOGGER.debug("Session: {}", savedSession);
    }
}