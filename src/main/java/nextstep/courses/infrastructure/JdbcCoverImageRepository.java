package nextstep.courses.infrastructure;

import nextstep.courses.domain.cover.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(CoverImage coverImage) {
        String sql = "insert into cover_image (file_size, image_type, width, height, created_at) " +
                "values (?, ?, ?, ?, NOW())";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, coverImage.getFileSize());
            ps.setString(2, coverImage.getType().getCode());
            ps.setInt(3, coverImage.getImageWidth());
            ps.setInt(4, coverImage.getImageHeight());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public CoverImage findById(Long id) {
        String sql = "SELECT id, file_size, image_type, width, height FROM cover_image WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            String imageType = rs.getString("image_type");
            return new CoverImage(
                    rs.getLong("id"),
                    new CoverImageFile(rs.getInt("file_size")),
                    CoverImageType.getCoverImageType(imageType),
                    new CoverImageSize(rs.getInt("width"), rs.getInt("height"))
            );
        }, id);
    }

    @Override
    public List<CoverImage> findBySessionId(Long sessionId) {
        String sql = "SELECT ci.* FROM cover_image ci " +
                "JOIN session_cover_images sci ON ci.id = sci.cover_image_id " +
                "WHERE sci.session_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String imageType = rs.getString("image_type");
            return new CoverImage(
                    rs.getLong("id"),
                    new CoverImageFile(rs.getInt("file_size")),
                    CoverImageType.getCoverImageType(imageType),
                    new CoverImageSize(rs.getInt("width"), rs.getInt("height"))
            );
        }, sessionId);
    }
}
