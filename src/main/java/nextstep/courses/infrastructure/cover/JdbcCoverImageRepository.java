package nextstep.courses.infrastructure.cover;

import nextstep.courses.entity.CoverImageEntity;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {

    private final static CoverImageRowMapper COVER_IMAGE_ENTITY_ROW_MAPPER = new CoverImageRowMapper();

    private final JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CoverImageEntity coverImageEntity, long sessionId) {
        String sql = "insert into cover_file "
                + "(file_path, session_id) "
                + "values(?, ?)";

        return jdbcTemplate.update(sql, coverImageEntity.getFilePath(), sessionId);
    }

    @Override
    public List<CoverImageEntity> findBySessionId(long sessionId) {
        String sql = "select "
                + "id, file_path, session_id "
                + "from cover_file "
                + "where session_id = ?";
        return jdbcTemplate.query(sql, COVER_IMAGE_ENTITY_ROW_MAPPER, sessionId);
    }

    private static class CoverImageRowMapper implements RowMapper<CoverImageEntity> {

        @Override
        public CoverImageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CoverImageEntity(
                    rs.getLong("id"),
                    rs.getString("file_path"),
                    rs.getString("session_id"));
        }
    }
}
