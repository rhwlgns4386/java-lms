package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.port.SessionCoverImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("jdbcSessionCoverImageRepository")
public class JdbcSessionCoverImageRepository implements SessionCoverImageRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SessionCoverImage findById(Long id) {
        String sql = "select id, session_id, volume, fileName, width, height from session_cover_image where id = ?";
        RowMapper<SessionCoverImage> rowMapper = (rs, rowNum) -> new SessionCoverImage(
                rs.getLong(1),
                rs.getLong(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getInt(6)
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int save(SessionCoverImage sessionCoverImage) {
        String sql = "insert into session_cover_image (session_id, fileName, volume ,extension, height, width) values(?,?,?,?,?,?)";

        return jdbcTemplate.update(sql, sessionCoverImage.getSessionId(), sessionCoverImage.getFileName(), sessionCoverImage.getCoverImageVolume().getSize(), sessionCoverImage.getCoverImageExtensionType().getExtension(), sessionCoverImage.getCoverImageFileSize().getHeight(), sessionCoverImage.getCoverImageFileSize().getWidth());
    }

}
