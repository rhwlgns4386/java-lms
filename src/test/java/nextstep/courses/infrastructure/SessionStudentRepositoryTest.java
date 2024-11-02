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

import java.util.Optional;

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
    void 조회시_없음(){
        Optional<SessionStudent> optionalSessionStudent = sessionStudentRepository.findBySessionIdAndNsUserId(2L, 3L);

        assertThat(optionalSessionStudent.isEmpty()).isTrue();
    }

    @Test
    void 저장후_조회(){
        SessionStudent sessionStudent = new SessionStudent(1L,1L);
        int count = sessionStudentRepository.save(sessionStudent);

        assertThat(count).isEqualTo(1);

        Optional<SessionStudent> optionalSessionStudent = sessionStudentRepository.findBySessionIdAndNsUserId(1L, 1L);

        assertThat(optionalSessionStudent.isPresent()).isTrue();
    }

}
