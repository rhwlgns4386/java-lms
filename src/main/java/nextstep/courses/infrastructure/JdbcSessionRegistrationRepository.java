package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.SessionRegistrationRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("sessionRegistrationRepository")
public class JdbcSessionRegistrationRepository implements SessionRegistrationRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRegistrationRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRegistrations(Long sessionId, List<Long> userIds) {
        String sql = "INSERT INTO session_registration (session_id, user_id, registered_at) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, userIds, userIds.size(),
                (ps, argument) -> {
                    ps.setLong(1, sessionId);
                    ps.setLong(2, argument);
                    ps.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
                });
    }

    @Override
    public List<Long> findRegisteredUserIds(Long sessionId) {
        String sql = "SELECT user_id FROM session_registration WHERE session_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, sessionId);
    }
}
