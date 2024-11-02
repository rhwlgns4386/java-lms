package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.session.SessionStudent;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("sessionStudentRepository")
public class JdbcSessionStudentRepository implements SessionStudentRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionStudent sessionStudent) {

        String sql = "insert into session_student(session_id, user_id) values (?, ?);";

        return jdbcTemplate.update(sql, sessionStudent.getSessionId(), sessionStudent.getUserId());
    }

    @Override
    public List<SessionStudent> findBySessionId(Long sessionId) {

        String sql = "select id, session_id, user_id from session_student where session_id = ?;";

        RowMapper<SessionStudent> rowMapper = (((rs, rowNum) -> new SessionStudent(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3)
        )));

        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }
}
