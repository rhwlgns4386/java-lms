package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionImageRepository;
import nextstep.courses.domain.Size;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcSessionImageRepository implements SessionImageRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionImage image) {
        String sql = "insert into session_image (session_id, file_path, file_size, width, height, creator_id, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                image.getSessionId(),
                image.getFilePath(),
                image.getFileSize(),
                image.getSize().getWidth(),
                image.getSize().getHeight(),
                image.getCreatorId(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Override
    public SessionImage findById(Long id) {
        String sql = "select id, session_id, file_path, file_size, width, height, creator_id, created_at, updated_at from session_image where id = ?";

        RowMapper<SessionImage> rowMapper = (rs, rowNum) -> new SessionImage(
                rs.getLong("id"),
                rs.getLong("session_id"),
                rs.getString("file_path"),
                rs.getInt("file_size"),
                new Size(rs.getInt("width"), rs.getInt("height")),
                rs.getLong("creator_id"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<SessionImage> findBySessionId(Long sessionId) {
        String sql = "select id, file_path, file_size, width, height, creator_id, created_at, updated_at from session_image where session_id = ?";

        RowMapper<SessionImage> rowMapper = (rs, rowNum) -> new SessionImage(
                rs.getLong("id"),
                rs.getString("file_path"),
                rs.getInt("file_size"),
                new Size(rs.getInt("width"), rs.getInt("height")),
                rs.getLong("creator_id"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );

        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
