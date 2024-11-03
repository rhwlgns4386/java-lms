package nextstep.courses.infrastructure.session;

import nextstep.courses.entity.SessionEntity;
import nextstep.courses.entity.SessionEntityTest;
import nextstep.courses.infrastructure.cover.JdbcCoverImageRepository;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, new JdbcCoverImageRepository(jdbcTemplate));
    }

    @Test
    @Transactional
    void updateEntity() {
        SessionEntity sessionEntity = SessionEntityTest.getSaveEntity();
        sessionRepository.save(sessionEntity, 1L);

        SessionEntity updateEntity = getUpdateEntity();
        sessionRepository.save(updateEntity, 1L);

        SessionEntity updatedSessionEntity = sessionRepository.findById(3L);
        assertThat(updatedSessionEntity.getEnrollment()).isEqualTo(1);
        assertThat(updatedSessionEntity.getCoverImageEntities()).hasSize(1);
        assertThat(updatedSessionEntity.getCoverImageEntities().get(0).getFilePath())
                .isEqualTo("src/test/java/nextstep/courses/domain/session/file/image.png");
    }

    private SessionEntity getUpdateEntity() {
        List<String> coverFilePaths = List.of("src/test/java/nextstep/courses/domain/session/file/image.png");
        return new SessionEntity(3L, "src/test/java/nextstep/courses/domain/session/file/image.png",
                coverFilePaths, SessionState.PROGRESS, RecruitState.RECRUIT, 1, 30,
                10000, LocalDateTime.now(), LocalDateTime.now());
    }
}
