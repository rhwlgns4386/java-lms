package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStudentRepository;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionStudentRepository")
public class JdbcSessionStudentRepository implements SessionStudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void registerStudentToSession(Long sessionId, NsUser student) {
        String sql = "INSERT INTO session_student (session_id, student_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, sessionId, student.getId());
    }

    @Override
    public List<Long> getRegisteredStudentIds(Long sessionId) {
        String sql = "SELECT student_id FROM session_student WHERE session_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("student_id"), sessionId);
    }
}
