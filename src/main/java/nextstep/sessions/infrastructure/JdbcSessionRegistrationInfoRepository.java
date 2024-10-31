package nextstep.sessions.infrastructure;

import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.sessions.domain.SessionRegistrationInfoRepository;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class JdbcSessionRegistrationInfoRepository implements SessionRegistrationInfoRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRegistrationInfoRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SessionRegistrationInfo> findBySessionId(Long sessionId) {
        String sql = "select session_id, user_id, created_at, updated_at" +
                " from session_registration_info" +
                " where session_id = ?";

        RowMapper<SessionRegistrationInfo> mapper = (rs, rowNum) -> new SessionRegistrationInfo(
                new NsUser(rs.getLong("user_id")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
        return jdbcTemplate.query(sql, mapper, sessionId);
    }

    @Override
    public int save(SessionRegistrationInfo sessionRegistrationInfo) {
        String sql = "insert into session_registration_info (session_id, user_id, created_at, updated_at)" +
                " values (?, ?, now(), now())";
        return jdbcTemplate.update(sql, sessionRegistrationInfo.getSessionId(), sessionRegistrationInfo.getUserId());
    }
}
