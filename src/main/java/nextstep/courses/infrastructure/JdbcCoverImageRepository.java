package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Long sessionId, CoverImage coverImage) {
        String sql = "insert into cover_image ( session_id, file_size, image_type, width, height, created_at) values (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                sessionId,
                coverImage.getFile().getSize(),
                coverImage.getType().name(),
                coverImage.getImageSize().getWidth(),
                coverImage.getImageSize().getHeight(),
                LocalDateTime.now()
        );
    }

    @Override
    public CoverImage findBySessionId(Long sessionId) {
        String sql = "select * from cover_image where session_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            CoverImageFile file = new CoverImageFile(rs.getInt("file_size"));

            CoverImageType type = CoverImageType.valueOf(
                    rs.getString("image_type")
            );

            CoverImageSize size = new CoverImageSize(
                    rs.getInt("width"),
                    rs.getInt("height")
            );

            return new CoverImage(file, type, size);
        }, sessionId);
    }
}
