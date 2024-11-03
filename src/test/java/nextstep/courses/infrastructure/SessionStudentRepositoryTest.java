package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.session.SessionStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionStudentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionStudentRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionStudentRepository sessionStudentRepository;

    @BeforeEach
    void setUp() {
        sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
    }

    @Test
    void 저장() {
        SessionStudent sessionStudent = new SessionStudent(1L, 4L);
        int count = sessionStudentRepository.save(sessionStudent);

        assertThat(count).isEqualTo(1);
    }

    @Test
    void 리스트_조회() {
        List<SessionStudent> students = sessionStudentRepository.findBySessionId(1L);

        assertThat(students).hasSize(3);
    }

}
