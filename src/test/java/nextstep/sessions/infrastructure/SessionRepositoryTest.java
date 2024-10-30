package nextstep.sessions.infrastructure;

import nextstep.courses.domain.CourseTest;
import nextstep.sessions.domain.PeriodTest;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionImageTest;
import nextstep.sessions.domain.SessionRepository;
import nextstep.sessions.domain.SessionStatus;
import nextstep.sessions.domain.SessionTypeTest;
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
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void create_read_test() {
        Session session = new Session(1L, SessionImageTest.IMAGE, PeriodTest.PERIOD, SessionTypeTest.PAID_TYPE
                , SessionStatus.PREPARING, CourseTest.COURSE, LocalDateTime.now(), LocalDateTime.now());
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
        Session savedSession = sessionRepository.findById(1L).get();
        assertThat(session.getId()).isEqualTo(savedSession.getId());
        LOGGER.debug("Session: {}", savedSession);
    }

    @Test
    void update_test() {
        Session session = new Session(1L, SessionImageTest.IMAGE, PeriodTest.PERIOD, SessionTypeTest.PAID_TYPE
                , SessionStatus.PREPARING, CourseTest.COURSE, LocalDateTime.now(), LocalDateTime.now());
        int count = sessionRepository.save(session);
        session.updateToRecruiting();
        int updateCount = sessionRepository.update(session);
        Session updatedSession = sessionRepository.findById(1L).get();
        assertThat(session.getStatus()).isEqualTo(updatedSession.getStatus());

    }
}
