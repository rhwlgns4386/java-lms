package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository("sessionRegistrationRepository")
public class JdbcSessionRegistrationRepository implements SessionRegistrationRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRegistrationRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRegistrations(Long sessionId, List<Long> userIds) {
        String sql = "INSERT INTO session_registration " +
                "(session_id, user_id, registered_at, registration_status, selection_status) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, userIds, userIds.size(),
                (ps, userId) -> {
                    ps.setLong(1, sessionId);
                    ps.setLong(2, userId);
                    ps.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
                    ps.setString(4, SessionRegistrationStatus.PENDING.getCode());
                    ps.setString(5, StudentSelectionStatus.PENDING.getCode());
                });
    }

    @Override
    public List<Long> findRegisteredUserIds(Long sessionId) {
        String sql = "SELECT user_id FROM session_registration " +
                "WHERE session_id = ? AND registration_status = ?";

        return jdbcTemplate.queryForList(
                sql,
                Long.class,
                sessionId,
                SessionRegistrationStatus.APPROVED.getCode()
        );
    }
    @Override
    public Optional<SessionRegistration> findBySessionIdAndUserId(Long sessionId, Long userId) {
        String sql = "SELECT * FROM session_registration WHERE session_id = ? AND user_id = ?";

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, this::mapRowToSessionRegistration, sessionId, userId)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<SessionRegistration> findBySessionId(Long sessionId) {
        String sql = "SELECT * FROM session_registration WHERE session_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSessionRegistration, sessionId);
    }

    @Override
    public List<SessionRegistration> findPendingBySessionId(Long sessionId) {
        String sql = "SELECT * FROM session_registration " +
                "WHERE session_id = ? AND selection_status = ?";

        return jdbcTemplate.query(
                sql,
                this::mapRowToSessionRegistration,
                sessionId,
                StudentSelectionStatus.PENDING.getCode()
        );
    }

    @Override
    public List<SessionRegistration> findSelectedBySessionId(Long sessionId) {
        String sql = "SELECT * FROM session_registration " +
                "WHERE session_id = ? AND selection_status = ?";

        return jdbcTemplate.query(
                sql,
                this::mapRowToSessionRegistration,
                sessionId,
                StudentSelectionStatus.SELECTED.getCode()
        );
    }

    @Override
    public List<SessionRegistration> findNotSelectedBySessionId(Long sessionId) {
        String sql = "SELECT * FROM session_registration " +
                "WHERE session_id = ? AND selection_status = ?";

        return jdbcTemplate.query(
                sql,
                this::mapRowToSessionRegistration,
                sessionId,
                StudentSelectionStatus.NOT_SELECTED.getCode()
        );
    }

    @Override
    public void update(SessionRegistration registration) {
        String sql = "UPDATE session_registration " +
                "SET registration_status = ?, selection_status = ? " +
                "WHERE session_id = ? AND user_id = ?";

        int updatedRows = jdbcTemplate.update(
                sql,
                registration.getRegistrationStatus().getCode(),
                registration.getSelectionStatus().getCode(),
                registration.getSessionId(),
                registration.getUserId()
        );

        if (updatedRows == 0) {
            throw new IllegalStateException("수강신청 정보를 찾을 수 없습니다.");
        }
    }

    private SessionRegistration mapRowToSessionRegistration(ResultSet rs, int rowNum) throws SQLException {
        RegistrationState state = new RegistrationState(
                SessionRegistrationStatus.from(rs.getString("registration_status")),
                StudentSelectionStatus.from(rs.getString("selection_status"))
        );

        return new SessionRegistration(
                rs.getLong("session_id"),
                rs.getLong("user_id"),
                rs.getTimestamp("registered_at").toLocalDateTime(),
                state
        );
    }
}
