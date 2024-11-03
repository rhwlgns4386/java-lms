package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.RecruitmentStatus;
import nextstep.courses.domain.session.SessionProgress;
import nextstep.courses.domain.session.SessionStatus;
import nextstep.courses.domain.session.SessionStatusRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository("sessionStatusRepository")
public class JdbcSessionStatusRepository implements SessionStatusRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionStatusRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Long sessionId, SessionStatus sessionStatus) {
        String sql = "INSERT INTO session_status (session_id, progress_status, recruitment_status) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                sessionId,
                sessionStatus.getProgressCode(),
                sessionStatus.getRecruitmentCode()
        );
    }

    @Override
    public SessionStatus findBySessionId(Long sessionId) {
        String sql = "SELECT progress_status, recruitment_status " +
                "FROM session_status WHERE session_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new SessionStatus(
                        SessionProgress.from(rs.getString("progress_status")),
                        RecruitmentStatus.from(rs.getString("recruitment_status"))
                ), sessionId);
    }
}
