package nextstep.courses.infrastructure.session;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionBuilder;
import nextstep.courses.type.SessionState;
import nextstep.courses.type.SessionType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private final static SessionRowMapper<Session> sessionRowMapper = new SessionRowMapper<>();

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Session findById(Long sessionId) {
        String sql = "select "
                + "id, session_state, cover_file_path, session_fee, enrollment, max_enrollment, start_date, end_date "
                + "from session "
                + "where id = ?";

        return jdbcTemplate.queryForObject(sql, sessionRowMapper, sessionId);
    }

    @Override
    public int save(Session session, long courseId) {
        if (!session.isPersisted()) {
            return insert(session, courseId);
        }
        return update(session);
    }

    private int insert(Session session, long courseId) {
        String sql = "insert into session "
                + "(course_id, session_state, cover_file_path, session_fee, max_enrollment, start_date, end_date) "
                + "values(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, courseId, session.getSessionState(), session.getCoverFilePath(),
                session.getSessionFee(), session.getMaxEnrollment(), session.getStartDate(), session.getEndDate());
    }

    private int update(Session session) {
        String sql = "update session set enrollment = ? where id = ?";
        return jdbcTemplate.update(sql, session.getEnrollment(), session.getId());
    }

    @Override
    public List<Session> findByCourseId(long courseId) {
        String sql = "select "
                        + "id, session_state, cover_file_path, session_fee, enrollment, max_enrollment, start_date, end_date "
                    + "from session "
                    + "where course_id = ?";
        return jdbcTemplate.query(sql, sessionRowMapper, courseId);
    }

    private static class SessionRowMapper<T extends Session> implements RowMapper<Session> {

        @Override
        public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
            SessionBuilder sessionBuilder = SessionBuilder.builder()
                    .id(rs.getLong("id"))
                    .sessionState(SessionState.valueOf(rs.getString("session_state")))
                    .coverImage(rs.getString("cover_file_path"))
                    .enrollment(rs.getInt("enrollment"))
                    .maxEnrollment(rs.getInt("max_enrollment"))
                    .startDate(toLocalDateTime(rs.getTimestamp("start_date")))
                    .endDate(toLocalDateTime(rs.getTimestamp("end_date")));

            long sessionFee = rs.getLong("session_fee");
            if (sessionFee == 0) {
                return sessionBuilder.sessionType(SessionType.FREE).build();
            }
            return sessionBuilder.sessionType(SessionType.PAID).sessionFee(sessionFee).build();
        }

        private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
            if (timestamp == null) {
                return null;
            }
            return timestamp.toLocalDateTime();
        }
    }
}
