package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionApprovalStatus;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@JdbcTest
public class StudentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new JdbcStudentRepository(namedParameterJdbcTemplate);
    }

    @Test
    void update() {
        Student student = new Student(1L, 1L, 1L, SessionApprovalStatus.PENDING);
        student.updateApprovalStatus(SessionApprovalStatus.APPROVED);
        long studentId = studentRepository.save(student);
        assertThat(studentId).isEqualTo(1L);
    }

    @Test
    void findBySessionId() {
        Student student = new Student(1L, 1L);
        studentRepository.save(student);
        Student savedStudent = studentRepository.findBySessionId(1L).get(0);
        assertThat(savedStudent.getSessionId()).isEqualTo(student.getSessionId());
        LOGGER.debug("Student: {}", savedStudent);
    }

    @Test
    void crud() {
        Student student = new Student(1L, 1L);
        studentRepository.save(student);
        Student savedStudent = studentRepository.findById(1L).get();
        assertThat(savedStudent.getSessionId()).isEqualTo(student.getSessionId());
        LOGGER.debug("Student: {}", savedStudent);
    }
}
