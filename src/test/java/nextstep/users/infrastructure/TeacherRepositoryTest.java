package nextstep.users.infrastructure;

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

import nextstep.users.domain.NsTeacher;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.TeacherRepository;
import nextstep.users.domain.UserRepository;

@JdbcTest
public class TeacherRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacherRepository = new JdbcTeacherRepository(jdbcTemplate);
    }

    @DisplayName("강사 id로 강사를 찾는다")
    @Test
    void findById() {
        Optional<NsTeacher> nsUser = teacherRepository.findById(1L);
        assertThat(nsUser.isEmpty()).isFalse();
    }
}
