package nextstep.courses.infrastructure;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository("sessionSessionImageRepository")
public class JdbcSessionSessionImageRepository implements SessionSessionImageRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionSessionImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Long sessionId, List<Long> imageIds) {
        String sql = "insert into session_session_image (session_id, image_id) values (?, ?)";
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Long imageId = imageIds.get(0);
                ps.setLong(1, sessionId);
                ps.setLong(2, imageId);
            }

            @Override
            public int getBatchSize() {
                return imageIds.size();
            }
        };
        return Arrays.stream(jdbcTemplate.batchUpdate(sql, statementSetter))
                .sum();
    }

    @Override
    public List<Long> findBySessionId(Long sessionId) {
        String sql = "select image_id from session_session_image where session_id = ?";
        RowMapper<Long> rowMapper = ((rs, rowNum) -> rs.getLong("image_id"));
        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }
}
