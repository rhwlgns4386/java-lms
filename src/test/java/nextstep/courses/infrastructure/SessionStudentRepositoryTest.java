package nextstep.courses.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static nextstep.courses.domain.EnrollStatus.APPLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
public class SessionStudentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionStudentRepositoryTest.class);

    @Autowired
    private JdbcOperations jdbcTemplate;

    private SessionStudentRepository sessionStudentRepository;

    @BeforeEach
    void setUp() {
        sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        long sessionId = 1L;
        List<Long> userIds = List.of(1L, 2L);
        int count = sessionStudentRepository.save(sessionId, userIds);
        List<Long> findUserIds = sessionStudentRepository.findBySessionId(sessionId);
        assertAll(
                () -> assertThat(count).isEqualTo(2),
                () -> assertThat(findUserIds).isEqualTo(userIds)
        );
        LOGGER.info("Students: {}", findUserIds);
    }

    @Test
    @DisplayName("상태값을 추가해서 저장, 조회 기능 추가")
    void crud_new() {
        long sessionId = 1L;
        List<Long> userIds = List.of(1L, 2L);
        int count = sessionStudentRepository.saveNew(sessionId, userIds, APPLY);
        List<Long> findUserIds = sessionStudentRepository.findBySessionIdAndStatus(sessionId, APPLY);
        assertAll(
                () -> assertThat(count).isEqualTo(2),
                () -> assertThat(findUserIds).isEqualTo(userIds)
        );
        LOGGER.info("Students: {}", findUserIds);
    }
}
