package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;

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

    @DisplayName("Session id로 해당 강의 찾는다")
    @Test
    void findById() {
        Optional<Session> session1 = sessionRepository.findById(1L);
        Optional<Session> session2 = sessionRepository.findById(2L);
        assertThat(session1.isPresent()).isTrue();
        assertThat(session2.isPresent()).isTrue();
    }
}
