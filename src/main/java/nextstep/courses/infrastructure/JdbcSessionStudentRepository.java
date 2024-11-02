package nextstep.courses.infrastructure;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository("sessionStudentRepository")
public class JdbcSessionStudentRepository implements SessionStudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Long sessionId, List<Long> userIds) {
        String sql = "insert into session_student (session_id, user_id) values (?, ?)";
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Long userId = userIds.get(i);
                ps.setLong(1, sessionId);
                ps.setLong(2, userId);
            }

            @Override
            public int getBatchSize() {
                return userIds.size();
            }
        };

        return Arrays.stream(jdbcTemplate.batchUpdate(sql, statementSetter))
                .sum();
    }

    @Override
    public List<Long> findBySessionId(Long sessionId) {
        String sql = "select user_id from session_student where session_id = ?";
        RowMapper<Long> rowMapper = (rs, rowNum) -> rs.getLong("user_id");
        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }
}
