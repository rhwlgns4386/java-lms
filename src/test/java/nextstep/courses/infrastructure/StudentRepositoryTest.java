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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    void crud() {
        NsUser sanjigi = NsUserTest.SANJIGI;
        Long amount = 100_000L;
        Student student = new Student(amount, sanjigi.getId());
        Long sessionId = 1L;

        int count = studentRepository.save(student, sessionId);
        assertThat(count).isEqualTo(1);

        Student saveStudent = studentRepository.findById(sanjigi.getId(), sessionId)
                .orElseThrow();
        assertThat(saveStudent.getNsUserId()).isEqualTo(sanjigi.getId());
        assertThat(saveStudent.getAmount()).isEqualTo(amount);
    }

    @Test
    void findBySessionId() {
        Long sessionId = 1L;
        Long amount = 100_000L;

        Student student = new Student(amount, NsUserTest.SANJIGI.getId());
        studentRepository.save(student, sessionId);

        Student student2 = new Student(amount, NsUserTest.JAVAJIGI.getId());
        studentRepository.save(student2, sessionId);

        List<Student> students = studentRepository.findAllBySessionId(sessionId);
        assertThat(students).hasSize(2);
    }

    @Test
    void saveAll() {
        Long sessionId = 1L;
        Long amount = 100_000L;
        Student student1 = new Student(amount, NsUserTest.SANJIGI.getId());
        Student student2 = new Student(amount, NsUserTest.JAVAJIGI.getId());
        List<Student> students = Arrays.asList(student1, student2);
        int[] saved = studentRepository.saveAll(students, sessionId);
        assertThat(saved.length).isEqualTo(2);
    }
}
