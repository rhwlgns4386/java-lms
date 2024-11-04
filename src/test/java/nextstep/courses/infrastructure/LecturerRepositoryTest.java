package nextstep.courses.infrastructure;

import nextstep.courses.domain.Lecturer.Lecturer;
import nextstep.courses.domain.Lecturer.LecturerRepository;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class LecturerRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private LecturerRepository lecturerRepository;

    @BeforeEach
    void setUp() {
        lecturerRepository = new JdbcLecturerRepository(namedParameterJdbcTemplate);
    }

    @Test
    void crud() {
        Long sessionId = 1L;
        Lecturer lecturer = new Lecturer(NsUserTest.SANJIGI.getId());
        int save = lecturerRepository.save(lecturer, sessionId);
        assertThat(save).isEqualTo(1);
        Lecturer foundLecturer = lecturerRepository.findByNsUserId(NsUserTest.SANJIGI.getId()).orElseThrow();
        assertThat(foundLecturer).isEqualTo(lecturer);
    }
}
