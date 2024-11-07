package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    public static final RowMapper<Session> SESSION_ROW_MAPPER = (rs, rowNum) -> new Session(
            rs.getLong("id"),
            rs.getLong("course_id"),
            new SessionPeriod(rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate()),
            new SessionCoverImage(rs.getLong("file_size"), rs.getString("file_type"), rs.getInt("width"), rs.getInt("height")),
            SessionFeeType.valueOf(rs.getString("fee_type")),
            new SessionAmount(rs.getInt("amount")),
            rs.getInt("max_personnel"),
            SessionStatus.valueOf(rs.getString("status")));

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcSessionRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int save(Session session) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("courseId", session.getCourseId())
                .addValue("startDate", session.getPeriod().getStartDate())
                .addValue("endDate", session.getPeriod().getEndDate())
                .addValue("fileSize", session.getCoverImage().getFileSize())
                .addValue("fileType", session.getCoverImage().getFileType())
                .addValue("width", session.getCoverImage().getWidth())
                .addValue("height", session.getCoverImage().getHeight())
                .addValue("feeType", session.getFeeType().name())
                .addValue("amount", session.getAmount().getAmount())
                .addValue("maxPersonnel", session.getMaxPersonnel())
                .addValue("status", session.getStatus().name());


        if (session.getId() == null || session.getId() == 0) {
            String insertSql = "insert into session (course_id, start_date, end_date, file_size, file_type, width, height, fee_type, amount, max_personnel, status) values (:courseId, :startDate, :endDate, :fileSize, :fileType, :width, :height, :feeType, :amount, :maxPersonnel, :status)";
            return namedParameterJdbcTemplate.update(insertSql, params);
        }
        params.addValue("id", session.getId());
        String updateSql = "update session set course_id = :courseId, start_date = :startDate, end_date = :endDate, file_size = :fileSize, file_type = :fileType, width = :width, height = :height, fee_type = :feeType, amount = :amount, max_personnel = :maxPersonnel, status = :status where id = :id";
        return namedParameterJdbcTemplate.update(updateSql, params);
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, course_id, start_date, end_date, file_size, file_type, width, height, fee_type, amount, max_personnel, status from session where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, SESSION_ROW_MAPPER));
    }
}
