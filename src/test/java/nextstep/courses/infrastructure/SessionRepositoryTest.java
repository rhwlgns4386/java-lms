package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionCoverImageRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPay;
import nextstep.courses.domain.session.SessionPeriod;
import nextstep.courses.domain.session.SessionStatus;
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
    void crud() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 10, 30, 23, 59);

        SessionPeriod period = new SessionPeriod(startDate, endDate);
        Session session = Session.createPaid(1L, 2000L, period, 20);


        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);


        Session saved = sessionRepository.findById(2L);

        assertThat(saved.getSessionPay().getSessionPay()).isEqualTo(2000L);
        assertThat(saved.getStatus()).isEqualTo(SessionStatus.READY);


    }
}
