package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    public static final RowMapper<Session> SESSION_ROW_MAPPER = (rs, rowNum) -> new Session(
            rs.getLong(1),
            rs.getLong(2),
            new SessionPeriod(rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate()),
            new SessionCoverImage(rs.getLong(5), rs.getString(6), rs.getInt(7), rs.getInt(8)),
            SessionFeeType.valueOf(rs.getString(9)),
            new SessionAmount(rs.getInt(10)),
            rs.getInt(11),
            SessionStatus.valueOf(rs.getString(12)));

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session (course_id, start_date, end_date, file_size, file_type, width, height, fee_type, amount, max_personnel, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, session.getCourseId(), session.getPeriod().getStartDate(), session.getPeriod().getEndDate(), session.getCoverImage().getFileSize(), session.getCoverImage().getFileType(), session.getCoverImage().getWidth(), session.getCoverImage().getHeight(), session.getFeeType().name(), session.getAmount().getAmount(), session.getMaxPersonnel(), session.getStatus().name());
    }

    @Override
    public Session findById(Long id) {
        String sql = "select id, course_id, start_date, end_date, file_size, file_type, width, height, fee_type, amount, max_personnel, status from session where id = ?";
        return jdbcTemplate.queryForObject(sql, SESSION_ROW_MAPPER, id);
    }
}
