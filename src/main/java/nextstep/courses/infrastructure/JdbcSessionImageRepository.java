package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImage;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionImageRepository")
public class JdbcSessionImageRepository implements SessionImageRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionImage sessionImage) {
        String sql = "insert into session_image (volume, type, width, height) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, sessionImage.getVolume(), sessionImage.getType().name(), sessionImage.getSessionImageSize().getWitdh(), sessionImage.getSessionImageSize().getHeight());
    }

    @Override
    public SessionImage findById(Long id) {
        String sql = "select id, volume, type, width, height from session_image where id = ?";
        RowMapper<SessionImage> rowMapper = (rs, rowNum) -> new SessionImage(
                rs.getLong(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
