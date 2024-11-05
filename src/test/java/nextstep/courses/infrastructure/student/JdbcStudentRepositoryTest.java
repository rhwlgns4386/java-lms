package nextstep.courses.infrastructure.student;

import nextstep.courses.entity.StudentEntity;
import nextstep.courses.infrastructure.enrollment.JdbcStudentRepository;
import nextstep.courses.infrastructure.enrollment.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class JdbcStudentRepositoryTest {

    @Autowired
    private JdbcOperations jdbcTemplate;

    private StudentRepository studentRepository;

    @BeforeEach
    public void init() {
        this.studentRepository = new JdbcStudentRepository(jdbcTemplate);
    }

    @Test
    void find_by_session_id() {
        List<StudentEntity> studentEntities = studentRepository.findBySessionId(1L);
        studentEntities.forEach(entity -> System.out.println(entity.getStudent().getId()));
        assertThat(studentEntities).hasSize(2);
        assertThat(studentEntities.stream().map(entity -> entity.getStudent().getId()).collect(Collectors.toList()))
                .containsExactly(1L, 2L);
    }
}
