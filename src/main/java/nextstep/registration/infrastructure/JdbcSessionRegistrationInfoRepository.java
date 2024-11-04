package nextstep.registration.infrastructure;

import nextstep.registration.domain.RegistrationStatus;
import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.registration.domain.SessionRegistrationInfoRepository;
import nextstep.sessions.domain.Session;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("sessionRegistrationInfoRepository")
public class JdbcSessionRegistrationInfoRepository implements SessionRegistrationInfoRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRegistrationInfoRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SessionRegistrationInfo> findBySessionId(Long sessionId) {
        String sql = "select session_id, user_id, registration_info, created_at, updated_at" +
                " from session_registration_info" +
                " where session_id = ?";

        RowMapper<SessionRegistrationInfo> mapper = (rs, rowNum) -> new SessionRegistrationInfo(
                new NsUser(rs.getLong("user_id")),
                RegistrationStatus.valueOf(rs.getString("registration_status")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
        return jdbcTemplate.query(sql, mapper, sessionId);
    }

    @Override
    public Optional<SessionRegistrationInfo> findBySessionIdAndUserId(Long sessionId, Long userId) {
        String sql = "select session_id, user_id, registration_status, created_at, updated_at" +
                " from session_registration_info" +
                " where session_id = ? and user_id = ?";

        RowMapper<SessionRegistrationInfo> mapper = (rs, rowNum) -> new SessionRegistrationInfo(
                new Session(rs.getLong("session_id")),
                new NsUser(rs.getLong("user_id")),
                RegistrationStatus.valueOf(rs.getString("registration_status")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );

        return Optional.of(jdbcTemplate.queryForObject(sql, mapper, sessionId, userId));
    }

    @Override
    public int save(SessionRegistrationInfo sessionRegistrationInfo) {
        String sql = "insert into session_registration_info (session_id, user_id, created_at, updated_at)" +
                " values (?, ?, now(), now())";
        return jdbcTemplate.update(sql, sessionRegistrationInfo.getSessionId(), sessionRegistrationInfo.getUserId());
    }
}
