package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    public static final RowMapper<Session> SESSION_ROW_MAPPER = (rs, rowNum) -> new Session(
            rs.getLong("id"),
            rs.getLong("course_id"),
            new SessionPeriod(rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate()),
            SessionFeeType.valueOf(rs.getString("fee_type")),
            new SessionAmount(rs.getInt("amount")),
            rs.getInt("max_personnel"),
            SessionProgressStatus.valueOf(rs.getString("progress_status")),
            SessionRecruitment.valueOf(rs.getString("recruitment")),
            SessionApprovalStatus.valueOf(rs.getString("approval_status")));

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcSessionRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long save(Session session) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("courseId", session.getCourseId())
                .addValue("startDate", session.getPeriod().getStartDate())
                .addValue("endDate", session.getPeriod().getEndDate())
                .addValue("feeType", session.getFeeType().name())
                .addValue("amount", session.getAmount().getAmount())
                .addValue("maxPersonnel", session.getMaxPersonnel())
                .addValue("progressStatus", session.getProgressStatus().name())
                .addValue("recruitment", session.getRecruitment().name())
                .addValue("approvalStatus", session.getApprovalStatus().name());

        if (session.getId() == null || session.getId() == 0) {
            String insertSql = "insert into session (course_id, start_date, end_date, fee_type, amount, max_personnel, progress_status, recruitment, approval_status) values (:courseId, :startDate, :endDate, :feeType, :amount, :maxPersonnel, :progressStatus, :recruitment, :approvalStatus)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(insertSql, params, keyHolder, new String[]{"id"});
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
        params.addValue("id", session.getId());
        String updateSql = "update session set course_id = :courseId, start_date = :startDate, end_date = :endDate, fee_type = :feeType, amount = :amount, max_personnel = :maxPersonnel, progress_status = :progressStatus, recruitment = :recruitment, approval_status = :approvalStatus where id = :id";
        namedParameterJdbcTemplate.update(updateSql, params);
        return session.getId();
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, course_id, start_date, end_date, fee_type, amount, max_personnel, progress_status, recruitment, approval_status from session where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, SESSION_ROW_MAPPER));
    }
}
