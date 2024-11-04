package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImage;
import nextstep.courses.entity.SessionImageEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionImageRepository")
public class JdbcSessionImageRepository implements SessionImageRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionImage sessionImage) {
        String sql = "insert into session_image (volume, type, width, height) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, sessionImage.getVolume(), sessionImage.getType().name(), sessionImage.getSessionImageSize().getWidth(), sessionImage.getSessionImageSize().getHeight());
    }

    @Override
    public int save(SessionImage sessionImage, Long sessionId) {
        SessionImageEntity sessionImageEntity = SessionImageEntity.from(sessionImage, sessionId);
        String sql = "insert into session_image (volume, type, width, height, session_id) values(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, sessionImageEntity.getVolume(), sessionImageEntity.getType(), sessionImageEntity.getWidth(), sessionImageEntity.getHeight(), sessionImageEntity.getSessionId());
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

    @Override
    public List<SessionImage> findBySessionId(Long sessionId) {
        String sql = "select id, volume, type, width, height from session_image where session_id = ?";
        RowMapper<SessionImage> rowMapper = (rs, rowNum) -> new SessionImageEntity(
                rs.getLong(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5)).to();
        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }
}
