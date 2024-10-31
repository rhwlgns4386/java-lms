package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.courses.infrastructure.JdbcCourseRepository;
import nextstep.sessions.domain.*;
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
        Session session = new Session(new SessionPeriod("20250101", "20250501"), null, new SessionType(Long.valueOf(220000), 100));
        session.toCourse(new Course(1L, "TDD Clean Code", 1L, LocalDateTime.now(), null));
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);

        Session savedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(savedSession.getId()).isEqualTo(1L);
        assertThat(savedSession.getTypeCode()).isEqualTo(SessionTypeEnum.PAID.getTypeCode());
        assertThat(savedSession.getStatusCode()).isEqualTo(SessionStatusEnum.PREPARING.getValue());

        savedSession.modifyStatus(SessionStatusEnum.RECRUITING);
        sessionRepository.modifyStatus(savedSession);
        Session statusModifiedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(statusModifiedSession.getStatusCode()).isEqualTo(SessionStatusEnum.RECRUITING.getValue());

        statusModifiedSession.modifyPeriod(new SessionPeriod("20250201", "20250501"));
        sessionRepository.modifyPeriod(statusModifiedSession);
        Session periodModifiedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(statusModifiedSession.getStartDate()).isEqualTo("20250201");


        LOGGER.debug("Session: {}", savedSession);
    }
}
