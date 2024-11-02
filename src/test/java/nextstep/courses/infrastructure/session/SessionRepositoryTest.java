package nextstep.courses.infrastructure.session;

import nextstep.courses.entity.SessionEntity;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @Transactional
    void updateEntity() {
        SessionEntity sessionEntity = getSaveEntity();
        sessionRepository.save(sessionEntity, 1L);

        SessionEntity updateEntity = getUpdateEntity();
        sessionRepository.save(updateEntity, 1L);

        SessionEntity updatedSessionEntity = sessionRepository.findById(3L);
        assertThat(updatedSessionEntity.getEnrollment()).isEqualTo(1);
    }

    private SessionEntity getSaveEntity() {
        return getEntity(RecruitState.RECRUIT);
    }

    private SessionEntity getEntity(RecruitState recruitState) {
        return new SessionEntity("src/test/java/nextstep/courses/domain/session/file/image.png",
                SessionState.PROGRESS, recruitState, 0, 30,
                10000, LocalDateTime.now(), LocalDateTime.now());
    }

    private SessionEntity getUpdateEntity() {
        return new SessionEntity(3L, "src/test/java/nextstep/courses/domain/session/file/image.png",
                SessionState.PROGRESS, RecruitState.RECRUIT, 1, 30,
                10000, LocalDateTime.now(), LocalDateTime.now());
    }
}
