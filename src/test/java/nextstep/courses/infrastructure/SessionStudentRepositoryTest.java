package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.SessionStudent;
import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.Students;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

@JdbcTest
class SessionStudentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionStudentRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionStudentRepository sessionStudentRepository;

    @BeforeEach
    void setUp() {
        sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
    }

    @DisplayName("Students를 가지고 생성한 SessionStudent객체를 저장한다")
    @Test
    void save() {
        List<NsUser> studentList = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI,
            NsUserTest.GYEONGJAE
        );
        Students students = new Students(1L, studentList);
        int[] result = sessionStudentRepository.saveAll(students);

        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrder(1, 1, 1);
    }

    @DisplayName("SessionId에 해당하는 모든 SessionStudent를 가져온다")
    @Test
    void findAllSessionStudentWithSessionId() {
        List<NsUser> studentList = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI,
            NsUserTest.GYEONGJAE
        );
        Students students = new Students(1L, studentList);
        sessionStudentRepository.saveAll(students);

        List<SessionStudent> foundSessionStudents = sessionStudentRepository.findAllBySessionId(1L);

        List<Long> collectStudentIds = foundSessionStudents.stream()
            .map(SessionStudent::getStudentId)
            .collect(Collectors.toList());

        assertThat(foundSessionStudents).hasSize(3);
        assertThat(collectStudentIds).containsExactlyInAnyOrder(1L, 2L, 3L);
    }
}
