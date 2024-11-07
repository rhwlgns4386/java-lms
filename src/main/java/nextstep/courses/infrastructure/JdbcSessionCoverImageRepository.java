package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionCoverImage;
import nextstep.courses.domain.SessionCoverImageRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcSessionCoverImageRepository implements SessionCoverImageRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcSessionCoverImageRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int save(SessionCoverImage sessionCoverImage) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("sessionId", sessionCoverImage.getSessionId())
                .addValue("fileSize", sessionCoverImage.getFileSize())
                .addValue("fileType", sessionCoverImage.getFileType())
                .addValue("width", sessionCoverImage.getWidth())
                .addValue("height", sessionCoverImage.getHeight());
        String sql = "insert into session_cover_image (session_id, file_size, file_type, width, height) values (:sessionId, :fileSize, :fileType, :width, :height)";
        return namedParameterJdbcTemplate.update(sql, params);
    }
}
