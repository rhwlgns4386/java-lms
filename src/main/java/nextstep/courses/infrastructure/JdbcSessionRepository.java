package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session, Long courseId) {
        String sql = "insert into session (course_id, title, start_at, end_at, session_type, session_status, capacity, current_count, price,  created_at) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if (session instanceof FreeSession) {
            FreeSession freeSession = (FreeSession) session;
            return jdbcTemplate.update(sql, courseId, freeSession.getTitle(), freeSession.getSessionDate().getStart(), freeSession.getSessionDate().getEnd(), freeSession.getSessionType().name(), freeSession.getSessionStatus().name(), null, null, null, LocalDateTime.now());
        }

        PaidSession paidSession = (PaidSession) session;
        return jdbcTemplate.update(sql, courseId, paidSession.getTitle(), paidSession.getSessionDate().getStart(), paidSession.getSessionDate().getEnd(), paidSession.getSessionType().name(), paidSession.getSessionStatus().name(), paidSession.getCapacity().getCapacity(), paidSession.getCapacity().getCurrentCount(), paidSession.getFee().getPrice(), LocalDateTime.now());
    }

    @Override
    public Session findById(Long id) {
        String sql = "select id, title, start_at, end_at, session_type, session_status, capacity, current_count, price from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> {
            long sessionId = rs.getLong(1);
            String title = rs.getString(2);
            Timestamp startAt = rs.getTimestamp(3);
            Timestamp endAt = rs.getTimestamp(4);
            SessionDate date = new SessionDate(startAt.toLocalDateTime(), endAt.toLocalDateTime());
            SessionType type = SessionType.of(rs.getString(5));
            SessionStatus status = SessionStatus.valueOf(rs.getString(6));
            if (type.equals(SessionType.FREE)) {
                return new FreeSession(null, date, sessionId, title, status, type, new ArrayList<>());
            }

            SessionCapacity capacity = new SessionCapacity(rs.getInt(7), rs.getInt(8));
            Money money = new Money(rs.getLong(9));
            return new PaidSession(null, date, sessionId, title, status, type, capacity, money, new ArrayList<>());
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
