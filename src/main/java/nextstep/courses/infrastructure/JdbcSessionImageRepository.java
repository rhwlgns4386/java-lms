package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionImageRepository;
import nextstep.courses.domain.Images;

@Repository("sessionImageRepository")
public class JdbcSessionImageRepository implements SessionImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int[] saveAll(Images images) {
        String sql = "insert into session_image (session_id, image_id) values (?, ?)";

        List<SessionImage> sessionImages = images.getImages().stream()
            .map(it -> new SessionImage(images.getSessionId(), it.getId()))
            .collect(Collectors.toList());

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SessionImage sessionImage = sessionImages.get(i);
                ps.setLong(1, sessionImage.getSessionId());
                ps.setLong(2, sessionImage.getImageId());
            }

            @Override
            public int getBatchSize() {
                return images.size();
            }
        });
    }

    @Override
    public List<SessionImage> findAllBySessionId(long sessionId) {
        String sql = "select id, session_id, image_id from session_image where session_id = ?";

        RowMapper<SessionImage> rowMapper = (rs, rowNum) -> new SessionImage(
            rs.getLong("id"),
            rs.getLong("session_id"),
            rs.getLong("image_id")
        );

        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }
}
