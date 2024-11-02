package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import nextstep.fixture.SessionDateCreator;
import nextstep.fixture.SessionImageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcOperations jdbcTemplate;

    private SessionRepository sessionRepository;
    private SessionImageRepository sessionImageRepository;

    @BeforeEach
    void setUp() {
        sessionImageRepository = new JdbcSessionImageRepository(jdbcTemplate);
        SessionStudentRepository sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, sessionImageRepository, sessionStudentRepository);
    }

    @Test
    void crud_free() {
        SessionImage sessionImage = SessionImageCreator.standard();
        Session freeSession = new FreeSession(SessionDateCreator.standard(), sessionImage, RecruitingStatus.RECRUITING, List.of(1L));
        int count = sessionRepository.save(freeSession);
        sessionImageRepository.save(sessionImage);
        Session savedSession = sessionRepository.findById(1L);
        assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(freeSession.getSessionId()).isEqualTo(savedSession.getSessionId()),
                () -> assertThat(freeSession.getDate()).isEqualTo(savedSession.getDate()),
                () -> assertThat(freeSession.getImage()).isEqualTo(savedSession.getImage()),
                () -> assertThat(freeSession.getRecruitingStatus()).isEqualTo(savedSession.getRecruitingStatus()),
                () -> assertThat(freeSession.getStudents()).isEqualTo(savedSession.getStudents())
        );
        LOGGER.debug("Session: {}", savedSession);
    }

    @Test
    void crud_paid() {
        SessionImage sessionImage = SessionImageCreator.standard();
        PaidSession paidSession = new PaidSession(SessionDateCreator.standard(), sessionImage, RecruitingStatus.RECRUITING, List.of(1L), 80, 24000);
        int count = sessionRepository.save(paidSession);
        sessionImageRepository.save(sessionImage);
        PaidSession savedSession = (PaidSession) sessionRepository.findById(1L);
        assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(paidSession.getSessionId()).isEqualTo(savedSession.getSessionId()),
                () -> assertThat(paidSession.getDate()).isEqualTo(savedSession.getDate()),
                () -> assertThat(paidSession.getImage()).isEqualTo(savedSession.getImage()),
                () -> assertThat(paidSession.getRecruitingStatus()).isEqualTo(savedSession.getRecruitingStatus()),
                () -> assertThat(paidSession.getSessionFee()).isEqualTo(savedSession.getSessionFee()),
                () -> assertThat(paidSession.getMaxNumOfStudents()).isEqualTo(savedSession.getMaxNumOfStudents()),
                () -> assertThat(paidSession.getStudents()).isEqualTo(savedSession.getStudents())
        );
        LOGGER.debug("Session: {}", savedSession);
    }
}
