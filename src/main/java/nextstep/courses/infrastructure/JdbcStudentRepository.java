package nextstep.courses.infrastructure;

import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    public static final RowMapper<Student> STUDENT_ROW_MAPPER = (rs, rowNum) -> new Student(
            rs.getLong("id"),
            rs.getLong("ns_user_id"),
            rs.getLong("session_id"));

    private JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String sql = "insert into student (ns_user_id, session_id) values (?, ?)";
        return jdbcTemplate.update(sql, student.getNsUserId(), student.getSessionId());
    }

    @Override
    public Optional<Student> findById(Long id) {
        String sql = "select id, ns_user_id, session_id from student where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, STUDENT_ROW_MAPPER, id));
    }
}
