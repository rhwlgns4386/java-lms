package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.port.SessionCoverImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Objects;

@Repository("jdbcSessionCoverImageRepository")
public class JdbcSessionCoverImageRepository implements SessionCoverImageRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SessionCoverImage findById(Long id) {
        String sql = "select id, session_id, volume, fileName, width, height, file_path from session_cover_image where id = ?";
        RowMapper<SessionCoverImage> rowMapper = (rs, rowNum) -> new SessionCoverImage(
                rs.getLong(1),
                rs.getLong(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getString(7)
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public Long save(SessionCoverImage sessionCoverImage) {
        String sql = "insert into session_cover_image (session_id, fileName, volume ,extension, height, width, file_path) values(?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setLong(1, sessionCoverImage.getSessionId());
            ps.setString(2, sessionCoverImage.getFileName());
            ps.setInt(3, sessionCoverImage.getCoverImageVolume().getSize());
            ps.setString(4, sessionCoverImage.getCoverImageExtensionType().getExtension());
            ps.setInt(5, sessionCoverImage.getCoverImageFileSize().getHeight());
            ps.setInt(6, sessionCoverImage.getCoverImageFileSize().getWidth());
            ps.setString(7, sessionCoverImage.getFilePath());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

}
