package nextstep.courses.infrastructure;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImage;
import nextstep.fixture.FreeSessionBuilder;
import nextstep.fixture.PaidSessionBuilder;
import nextstep.fixture.SessionImageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static nextstep.courses.domain.ProgressStatus.PROGRESSING;
import static nextstep.courses.domain.RecruitingStatus.RECRUITING;
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
        SessionImage sessionImage1 = SessionImageCreator.id(1L);
        SessionImage sessionImage2 = SessionImageCreator.id(2L);
        sessionImageRepository.save(sessionImage1, 1L);
        sessionImageRepository.save(sessionImage2,1L);
        Session freeSession = new FreeSessionBuilder()
                .withId(1L)
                .withImages(List.of(sessionImage1, sessionImage2))
                .withRecruitingStatus(RECRUITING)
                .withProgressingStatus(PROGRESSING)
                .withApplyStudents(List.of(1L))
                .build();
        int count = sessionRepository.saveNew(freeSession);
        Session savedSession = sessionRepository.findByIdNew(1L);
        assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(freeSession.getSessionId()).isEqualTo(savedSession.getSessionId()),
                () -> assertThat(freeSession.getDate()).isEqualTo(savedSession.getDate()),
                () -> assertThat(freeSession.getImages()).isEqualTo(savedSession.getImages()),
                () -> assertThat(freeSession.getRecruitingStatus()).isEqualTo(savedSession.getRecruitingStatus()),
                () -> assertThat(freeSession.getProgressStatus()).isEqualTo(savedSession.getProgressStatus()),
                () -> assertThat(freeSession.getStudents()).isEqualTo(savedSession.getStudents())
        );
        LOGGER.debug("Session: {}", savedSession);
    }

    @Test
    void crud_paid() {
        SessionImage sessionImage1 = SessionImageCreator.id(1L);
        SessionImage sessionImage2 = SessionImageCreator.id(2L);
        sessionImageRepository.save(sessionImage1,1L);
        sessionImageRepository.save(sessionImage2,1L);
        PaidSession paidSession = new PaidSessionBuilder()
                .withId(1L)
                .withImages(List.of(sessionImage1, sessionImage2))
                .withRecruitingStatus(RECRUITING)
                .withProgressingStatus(PROGRESSING)
                .withApplyStudents(List.of(2L))
                .build();
        int count = sessionRepository.saveNew(paidSession);
        PaidSession savedSession = (PaidSession) sessionRepository.findByIdNew(1L);
        assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(paidSession.getSessionId()).isEqualTo(savedSession.getSessionId()),
                () -> assertThat(paidSession.getDate()).isEqualTo(savedSession.getDate()),
                () -> assertThat(paidSession.getImages()).isEqualTo(savedSession.getImages()),
                () -> assertThat(paidSession.getRecruitingStatus()).isEqualTo(savedSession.getRecruitingStatus()),
                () -> assertThat(paidSession.getProgressStatus()).isEqualTo(savedSession.getProgressStatus()),
                () -> assertThat(paidSession.getSessionFee()).isEqualTo(savedSession.getSessionFee()),
                () -> assertThat(paidSession.getMaxNumOfStudents()).isEqualTo(savedSession.getMaxNumOfStudents()),
                () -> assertThat(paidSession.getStudents()).isEqualTo(savedSession.getStudents()),
                () -> assertThat(paidSession.getApplyStudents()).isEqualTo(savedSession.getApplyStudents())
        );
        LOGGER.debug("Session: {}", savedSession);
    }
}
