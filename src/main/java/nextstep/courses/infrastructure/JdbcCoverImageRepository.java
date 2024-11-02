package nextstep.courses.infrastructure;

import nextstep.courses.domain.cover.CoverImageRepository;
import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CoverImage coverImage) {
        String sql = "insert into cover_image (file_size, image_type, width, height, created_at) " +
                "values (?, ?, ?, ?, ?)";  // 값 5개만 받도록 수정

        CoverImageEntity entity = CoverImageEntity.from(coverImage);

        return jdbcTemplate.update(sql,
                entity.getFileSize(),
                entity.getImageType(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getCreatedAt()
        );
    }

    @Override
    public CoverImage findById(Long id) {
        String sql = "select id, file_size, image_type, width, height from cover_image where id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            CoverImageEntity entity = new CoverImageEntity();
            entity.setId(rs.getLong("id"));
            entity.setFileSize(rs.getInt("file_size"));
            entity.setImageType(rs.getString("image_type"));
            entity.setWidth(rs.getInt("width"));
            entity.setHeight(rs.getInt("height"));
            return entity.toDomain();
        }, id);
    }
}
