package nextstep.courses.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.courses.domain.Extension;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageMetaData;
import nextstep.courses.domain.ImageRepository;
import nextstep.courses.domain.ImageSize;

@Repository("imageRepository")
public class JdbcImageRepository implements ImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Image image) {
        String sql = "insert into image (byte_size, width, height, extension) "
            + "values (?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
            image.getMetaInfo().getByteSize(),
            image.getImageSize().getWidth(),
            image.getImageSize().getHeight(),
            image.getMetaInfo().getExtension());
    }

    @Override
    public Optional<Image> findById(Long id) {
        String sql = "select id, byte_size, width, height, extension from image where id = ?";

        RowMapper<Image> rowMapper = (rs, rowNum) -> new Image(
            rs.getLong(1),
            new ImageSize(
                rs.getInt(3),
                rs.getInt(4)
            ),
            new ImageMetaData(
                rs.getInt(2),
                Extension.getWithString(rs.getString(5))
            )
        );

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
