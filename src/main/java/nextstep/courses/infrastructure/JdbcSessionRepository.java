package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.session.DefaultSession;
import nextstep.courses.domain.session.FreeSession;
import nextstep.courses.domain.session.PaidSession;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveFreeSession(FreeSession session) {
        return 0L;
    }

    @Override
    public Long savePaidSession(PaidSession session) {
        return 0L;
    }

    @Override
    public DefaultSession findById(Long id) {
        return null;
    }
}
