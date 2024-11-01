package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("imageRepository")
public class JdbcImageRepository implements ImageRepository {
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
        RowMapper<Image> rowMapper = (rs, rowNum) -> {
            long imageId = rs.getLong(1);
            long size = rs.getLong(2);
            String imageType = rs.getString(3);
            long width = rs.getLong(4);
            long height = rs.getLong(5);

            ImageSize imageSize = new ImageSize(size);
            ImageType type = ImageType.of(imageType);
            ImagePixel imagePixel = new ImagePixel(width, height);

            return new Image(imageId, imageSize, type, imagePixel);
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
