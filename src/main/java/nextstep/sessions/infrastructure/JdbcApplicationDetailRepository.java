package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.sessions.domain.ApplicationDetail;
import nextstep.sessions.domain.ApplicationDetailRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("applicationDetailRepository")
public class JdbcApplicationDetailRepository implements ApplicationDetailRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcApplicationDetailRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(ApplicationDetail applicationDetail) {
        String sql = "insert into application_detail (session_id, user_id, created_at) values(?,?,?)";
        return jdbcTemplate.update(sql, applicationDetail.getSessionId(), applicationDetail.getNsUserId(), applicationDetail.getCreatedAt());
    }

    @Override
    public Optional<ApplicationDetail> findByUserAndSession(Long sessionId, Long nsUserId) {
        String sql = "select session_id, user_id, created_at, updated_at from application_detail where session_id =? and user_id =?";
        RowMapper<ApplicationDetail> rowMapper = (rs, rowNum)  -> new ApplicationDetail(
                rs.getLong("session_id"),
                rs.getLong("user_id"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at")));
        return Optional.of(jdbcTemplate.queryForObject(sql,rowMapper,sessionId,nsUserId));
    }

    @Override
    public List<ApplicationDetail> findBySession(Long sessionId) {
        String sql = "select session_id, user_id,  created_at, updated_at from application_detail where session_id=?";
        RowMapper<ApplicationDetail> rowMapper = (rs, rowNum) -> new ApplicationDetail(
                rs.getLong("session_id"),
                rs.getLong("user_id"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at")));
        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
