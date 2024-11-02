package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionRegistrationRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.session.DefaultSession;
import nextstep.courses.domain.session.FreeSession;
import nextstep.courses.domain.session.PaidSession;
import nextstep.courses.domain.session.SessionRegistrationEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("sessionRegistrationRepository")
public class JdbcSessionRegistrationRepository implements SessionRegistrationRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRegistrationRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRegistrations(List<SessionRegistrationEntity> registrations) {
        if (registrations.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO session_registration (session_id, user_id, registered_at) " +
                "VALUES (?, ?, ?)";

        List<Object[]> batchArgs = registrations.stream()
                .map(reg -> new Object[]{
                        reg.getSessionId(),
                        reg.getUserId(),
                        Timestamp.valueOf(reg.getRegisteredAt())
                })
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public List<Long> findRegisteredUserIds(Long sessionId) {
        String sql = "SELECT user_id FROM session_registration WHERE session_id = ?";

        return new ArrayList<>(jdbcTemplate.queryForList(sql, Long.class, sessionId));
    }
}
