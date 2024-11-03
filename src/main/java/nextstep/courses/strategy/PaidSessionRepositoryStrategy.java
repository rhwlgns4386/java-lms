package nextstep.courses.strategy;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import org.springframework.jdbc.core.JdbcOperations;

public class PaidSessionRepositoryStrategy implements SessionRepositoryStrategy{

    private final JdbcOperations jdbcTemplate;

    public PaidSessionRepositoryStrategy(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveRegisterSession(Session session) {
        String sql = "insert into session (title, apply_start_date, apply_end_date, sale_price, state_code, creator_id, session_type, student_max_count) " +
                "values(?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, session.getTitle(), session.getApplyStartDate(), session.getApplyEndDate(), session.getSalePrice(),
                session.getStateCode(), session.getCreatorId(), session.getSessionTypeCode(), ((PaidSession) session).getStudentMaxCount());
    }
}
