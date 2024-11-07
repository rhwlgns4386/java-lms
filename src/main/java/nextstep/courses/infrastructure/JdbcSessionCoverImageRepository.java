package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionCoverImageRepository;
import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.courses.domain.coverimage.SessionCoverImagePath;
import nextstep.courses.domain.coverimage.SessionCoverImageSize;
import nextstep.courses.domain.coverimage.SessionCoverImages;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository("sessionCoverImageRepository")
public class JdbcSessionCoverImageRepository implements SessionCoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionCoverImage image) {

        String sql = "insert into session_cover_image (session_id, stored_file_name, original_file_name, width, height) values (?, ?, ?, ?, ?);";

        SessionCoverImagePath path = image.getSessionCoverImagePath();
        SessionCoverImageSize size = image.getSessionCoverImageSize();
        return jdbcTemplate.update(sql, image.getSessionId(), path.getStoreFileName(), path.getOriginalFileName(), size.getWidth(), size.getHeight());

    }

    @Override
    public SessionCoverImage findById(Long id) {

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append("id, session_id, original_file_name, stored_file_name, width, height ");
        sb.append("from session_cover_image ");
        sb.append("where id = ?");
        RowMapper<SessionCoverImage> rowMapper = (rs, rowNum) -> new SessionCoverImage(
                rs.getLong(1),
                rs.getLong(2),
                new SessionCoverImagePath(rs.getString(3), rs.getString(4)),
                new SessionCoverImageSize(rs.getInt(5), rs.getInt(6))
        );


        return jdbcTemplate.queryForObject(sb.toString(), rowMapper, id);
    }

    @Override
    public void saveAll(SessionCoverImages images) {
        String sql = "insert into session_cover_image (session_id, stored_file_name, original_file_name, width, height) values (?, ?, ?, ?, ?);";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SessionCoverImage image = images.get(i);
                SessionCoverImagePath path = image.getSessionCoverImagePath();
                SessionCoverImageSize size = image.getSessionCoverImageSize();

                ps.setLong(1, image.getSessionId());
                ps.setString(2, path.getStoreFileName());
                ps.setString(3, path.getOriginalFileName());
                ps.setInt(4, size.getWidth());
                ps.setInt(5, size.getHeight());
            }

            @Override
            public int getBatchSize() {
                return images.getSize();
            }
        });

    }

}
