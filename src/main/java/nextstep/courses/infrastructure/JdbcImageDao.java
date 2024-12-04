package nextstep.courses.infrastructure;

import java.util.ArrayList;
import java.util.List;
import nextstep.courses.domain.CoverImage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcImageDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcImageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CoverImage> findAllBySessionId(Long id) {
        String sql = "select session_id,image_id from cover_image where session_id = ?";

        RowMapper<CoverImage> rowMapper = (rs, rowNum) -> {
            long sessionId = rs.getLong("session_id");
            long userId = rs.getLong("image_id");
            return new CoverImage(sessionId, userId);
        };
        return new ArrayList<>(jdbcTemplate.query(sql, rowMapper, id));
    }
}
