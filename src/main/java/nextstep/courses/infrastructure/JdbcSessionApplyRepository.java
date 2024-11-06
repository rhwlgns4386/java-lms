package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionApplyRepository;
import nextstep.courses.domain.session.SessionApply;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository("sessionApplyRepository")
public class JdbcSessionApplyRepository implements SessionApplyRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionApplyRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionApply apply) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into session_apply ");
        sb.append("(session_id, user_id, is_guest, is_submit, is_deleted ) ");
        sb.append("values");
        sb.append("(?,?,?,?,?);");

        return jdbcTemplate.update(sb.toString(),
                apply.getSessionId(),
                apply.getUserId(),
                apply.isGuest(),
                apply.isSubmit(),
                apply.isDeleted()
        );
    }

    @Override
    public SessionApply findById(Long id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(" id, session_id, user_id, ");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        return null;
    }
}
