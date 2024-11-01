package nextstep.courses.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
public class SessionStudentRepositoryTest {

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
    }
}
