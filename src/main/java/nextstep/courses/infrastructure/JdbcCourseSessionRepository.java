package nextstep.courses.infrastructure;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository("courseSessionRepository")
public class JdbcCourseSessionRepository implements CourseSessionRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcCourseSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Long courseId, List<Long> sessionIds) {
        String sql = "insert into course_session (course_id, session_id) values (?, ?)";
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Long sessionId = sessionIds.get(i);
                ps.setLong(1, courseId);
                ps.setLong(2, sessionId);
            }

            @Override
            public int getBatchSize() {
                return sessionIds.size();
            }
        };

        return Arrays.stream(jdbcTemplate.batchUpdate(sql, statementSetter))
                .sum();
    }

    @Override
    public List<Long> findByCourseId(Long courseId) {
        String sql = "select session_id from course_session where course_id = ?";
        RowMapper<Long> rowMapper = (rs, rowNum) -> rs.getLong("session_id");
        return jdbcTemplate.query(sql, rowMapper, courseId);
    }
}
