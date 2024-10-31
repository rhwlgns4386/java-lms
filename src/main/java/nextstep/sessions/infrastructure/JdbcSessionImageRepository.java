package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.sessions.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository("sessionImageRepository")
public class JdbcSessionImageRepository implements SessionImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionImage sessionImage) {
        String sql = "insert into session_image (url, size_of_image, width, height, type_of_image, session_id, created_at) " +
                "values(?, ?, ?, ?, ?,?, ?)";
        return this.jdbcTemplate.update(sql, sessionImage.getUrl(), sessionImage.getImageSize().getSize()
                ,sessionImage.getImagePixel().getWidth().getWidth(), sessionImage.getImagePixel().getHeight().getHeight()
                ,sessionImage.getImageType().getValue(), sessionImage.getSession().getId(), LocalDateTime.now());
    }

    @Override
    public Optional<SessionImage> findBySession(Long sessionId) {
        String sql = "select url, size_of_image, width, height, type_of_image, session_id, created_at, updated_at from session_image where session_id=?";
        RowMapper<SessionImage> rowMapper = (rs, rowNum) -> new SessionImage(
                rs.getString("url"),
                new ImageSize(rs.getLong("size_of_image")),
                SessionImageTypeEnum.getByValue(rs.getString("type_of_image")),
                new SessionImagePixel(new ImageWidth(rs.getInt("width")), new ImageHeight(rs.getInt("height"))));

        return Optional.of(this.jdbcTemplate.queryForObject(sql, rowMapper, sessionId));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
