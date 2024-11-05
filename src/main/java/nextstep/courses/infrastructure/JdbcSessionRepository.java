package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
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
    public Optional<Session> findById(Long id) {
        String sql = "select id, course_id, start_date, end_date, file_size, file_type, width, height, fee_type, amount, max_personnel, status from session where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, SESSION_ROW_MAPPER, id));
    }
}
