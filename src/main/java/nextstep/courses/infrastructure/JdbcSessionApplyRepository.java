package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionApplyRepository;
import nextstep.courses.domain.session.SessionApply;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
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
        sb.append("(session_id, user_id, is_selection, is_submit, is_deleted ) ");
        sb.append("values");
        sb.append("(?,?,?,?,?);");

        return jdbcTemplate.update(sb.toString(),
                apply.getSessionId(),
                apply.getUserId(),
                apply.isSelection(),
                apply.isSubmit(),
                apply.isDeleted()
        );
    }

    @Override
    public SessionApply findById(Long id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(" apply.id, apply.session_id, apply.user_id, ");
        sb.append("CASE");
        sb.append(" WHEN selection.id IS NULL THEN FALSE");
        sb.append(" ELSE TRUE ");
        sb.append("END as is_selection, ");
        sb.append("apply.is_submit, apply.is_deleted ");
        sb.append("from session_apply as apply ");
        sb.append("inner join ns_user as ns_user on apply.user_id = ns_user.id ");
        sb.append("left join selection_user as selection on selection.user_id = ns_user.id ");
        sb.append("where apply.id = ?");

        RowMapper<SessionApply> rowMapper = ((rs, rowNum) -> new SessionApply(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3),
                rs.getBoolean(4),
                rs.getBoolean(5),
                rs.getBoolean(6)
        ));
        return jdbcTemplate.queryForObject(sb.toString(), rowMapper, id);
    }

    @Override
    public void update(SessionApply apply) {

    }
}
