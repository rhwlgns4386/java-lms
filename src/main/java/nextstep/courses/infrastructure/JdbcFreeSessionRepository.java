package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("freeSessionRepository")
public class JdbcFreeSessionRepository extends JdbcSessionRepository<FreeSession> implements FreeSessionRepository {
    public JdbcFreeSessionRepository(JdbcOperations jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Long saveBaseSession(FreeSession session, Long courseId) {
        Long sessionId = super.saveBaseSession(session, courseId);

        String freeSessionSql = "UPDATE session SET  max_capacity =?,  fee=? where id=?";
        jdbcTemplate.update(freeSessionSql, 0, 0, sessionId);

        return sessionId;
    }

    @Override
    public FreeSession findById(Long id) {
        String sql =
                "SELECT s.id, s.title, s.session_start, s.session_end, s.image_size, s.image_width, s.image_height, s.image_type, s.session_status, s.course_id "
                        +
                        "FROM session s WHERE s.id = ?";
        RowMapper<FreeSession> rowMapper = (rs, rowNum) -> new FreeSession(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getDate("session_start").toLocalDate(),
                rs.getDate("session_end").toLocalDate(),
                rs.getString("session_status") != null ? SessionStatus.valueOf(rs.getString("session_status")) : null,
                new Students(),
                new SessionImage(
                        rs.getInt("image_size"),
                        rs.getInt("image_width"),
                        rs.getInt("image_height"),
                        rs.getString("image_type")
                )
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
