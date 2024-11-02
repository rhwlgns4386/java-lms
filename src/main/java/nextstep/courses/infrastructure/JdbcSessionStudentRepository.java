package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.session.SessionStudent;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("sessionStudentRepository")
public class JdbcSessionStudentRepository implements SessionStudentRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<SessionStudent> findBySessionIdAndNsUserId(Long sessionId, Long nsUserId) {
        StringBuilder sb = new StringBuilder();

        sb.append("select");
        sb.append(" session_id, user_id ");
        sb.append("from session_student ");
        sb.append("where session_id = ? and user_id = ? ");

        try {
            RowMapper<SessionStudent> rowMapper = ((rs, rowNum) -> new SessionStudent(
                    rs.getLong(1), rs.getLong(2)
            ));

            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sb.toString(),
                            rowMapper,
                            sessionId, nsUserId
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int save(SessionStudent sessionStudent) {

        String sql = "insert into session_student(session_id, user_id) values (?, ?);";

        return jdbcTemplate.update(sql, sessionStudent.getSessionId(), sessionStudent.getUserId());
    }
}
