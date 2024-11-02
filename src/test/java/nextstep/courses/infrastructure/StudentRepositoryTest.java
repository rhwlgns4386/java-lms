package nextstep.courses.infrastructure;

import nextstep.courses.domain.student.Student;
import nextstep.courses.domain.student.StudentRepository;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class StudentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new JdbcStudentRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        NsUser sanjigi = NsUserTest.SANJIGI;
        Long amount = 100_000L;
        Student student = new Student(amount, sanjigi.getId());
        Long sessionId = 1L;

        int count = studentRepository.save(student, sessionId);
        assertThat(count).isEqualTo(1);

        Student saveStudent = studentRepository.findById(sanjigi.getId(), sessionId);
        assertThat(saveStudent.getNsUserId()).isEqualTo(sanjigi.getId());
        assertThat(saveStudent.getAmount()).isEqualTo(amount);
    }
}
