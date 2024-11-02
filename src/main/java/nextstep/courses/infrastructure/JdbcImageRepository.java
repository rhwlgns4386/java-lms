package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("imageRepository")
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

    private JdbcOperations jdbcTemplate;

    public JdbcImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Image image, Long sessionId) {
        String sql = "insert into image (session_id, size, image_type, width, height, created_at) values(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, sessionId, image.getImageSize().getSize(), image.getImageType().name(), image.getImagePixel().getWidth(), image.getImagePixel().getHeight(), LocalDateTime.now());
    }

    @Override
    public Image findById(long id) {
        String sql = "select id, size, image_type, width, height from image where id = ?";
        return jdbcTemplate.queryForObject(sql, IMAGE_ROW_MAPPER, id);
    }

    @Override
    public Image findBySessionId(long sessionId) {
        String sql = "select id, size, image_type, width, height from image where session_id = ?";
        return jdbcTemplate.queryForObject(sql, IMAGE_ROW_MAPPER, sessionId);
    }
}
