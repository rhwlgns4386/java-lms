package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class JdbcImageRepository implements ImageRepository {
    private static final RowMapper<Image> IMAGE_ROW_MAPPER = (rs, rowNum) -> {
        long imageId = rs.getLong(1);
        long size = rs.getLong(2);
        String imageType = rs.getString(3);
        int width = rs.getInt(4);
        int height = rs.getInt(5);

        ImageSize imageSize = new ImageSize(size);
        ImageType type = ImageType.of(imageType);
        ImagePixel imagePixel = new ImagePixel(width, height);

        return new Image(imageId, imageSize, type, imagePixel);
    };

    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    public JdbcImageRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int save(Image image, Long sessionId) {
        String sql = "insert into image (session_id, size, image_type, width, height, created_at) values(:sessionId, :size, :imageType, :width, :height, :createdAt)";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("sessionId", sessionId);
        param.addValue("size", image.getImageSize().getSize());
        param.addValue("imageType", image.getImageType().name());
        param.addValue("width", image.getImagePixel().getWidth());
        param.addValue("height", image.getImagePixel().getHeight());
        param.addValue("createdAt", LocalDateTime.now());
        return namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public Optional<Image> findById(long id) {
        String sql = "select id, size, image_type, width, height from image where id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, param, IMAGE_ROW_MAPPER));
    }

    @Override
    public Optional<Image> findBySessionId(long sessionId) {
        String sql = "select id, size, image_type, width, height from image where session_id = :sessionId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("sessionId", sessionId);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, param, IMAGE_ROW_MAPPER));
    }
}
