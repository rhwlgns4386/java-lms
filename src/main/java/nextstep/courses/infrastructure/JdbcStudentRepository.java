package nextstep.courses.infrastructure;

import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student, Long sessionId) {
        String sql = "insert into student (ns_user_id, session_id, amount) values(?,?,?)";
        return jdbcTemplate.update(sql, student.getNsUserId(), sessionId, student.getAmount());
    }

    @Override
    public Student findById(Long nsUserId, Long sessionId) {
        String sql = "select ns_user_id, amount from student where ns_user_id = ? and session_id = ?";
        RowMapper<Student> rowMapper = (rs, rowNum) -> new Student(
                rs.getLong(2),
                rs.getLong(1)
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, nsUserId, sessionId);
    }
}
