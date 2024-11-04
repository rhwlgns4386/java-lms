package nextstep.sessions.infrastructure;

import nextstep.sessions.domain.ImageType;
import nextstep.sessions.domain.CoverImage;
import nextstep.sessions.domain.SessionImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository("sessionImageRepository")
public class JdbcSessionImageRepository implements SessionImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CoverImage save(CoverImage image) {
        String imageSql = "insert into session_image (size, type, width, height) values (?, ?, ?, ?)";
        KeyHolder keyHolderForImage = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(imageSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, image.getSize());
            ps.setString(2, image.getType());
            ps.setDouble(3, image.getWidth());
            ps.setDouble(4, image.getHeight());
            return ps;
        }, keyHolderForImage);
        Long imageId = keyHolderForImage.getKey().longValue();
        image.setId(imageId);
        return image;
    }

    @Override
    public CoverImage saveWithSessionId(CoverImage image, Long sessionId) {
        image = this.save(image);
        String sql = "insert into session_cover_image (session_id, cover_image_id) values (?, ?)";
        jdbcTemplate.update(sql, sessionId, image.getId());
        return image;
    }

    @Override
    public CoverImage findById(Long imageId) {
        String sql = "select id, size, type, width, height from session_image where id = ?";
        RowMapper<CoverImage> rowMapper = (rs, rowNum) -> new CoverImage(
                rs.getLong(1)
                , rs.getInt(2)
                , ImageType.of(rs.getString(3))
                , rs.getDouble(4)
                , rs.getDouble(5));

        return jdbcTemplate.queryForObject(sql, rowMapper, imageId);
    }

    @Override
    public List<CoverImage> findAllBySessionId(Long sessionId) {
        String sql = "select c.id, c.size, c.width, c.type, c.height" +
                " from session_cover_image sci" +
                " join cover_image c on sci.cover_image_id = c.id" +
                " where sci.session_id = ?";

        RowMapper<CoverImage> mapper = (rs, rowNum) -> new CoverImage(
                rs.getLong(1),
                rs.getInt(2),
                ImageType.of(rs.getString(3)),
                rs.getDouble(4),
                rs.getDouble(5)
        );

        return jdbcTemplate.query(sql, mapper, sessionId);
    }
}
