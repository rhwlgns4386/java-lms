package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session (start_date, end_date, file_size, file_type, width, height, fee_type, amount, max_personnel, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, session.getPeriod().getStartDate(), session.getPeriod().getEndDate(), session.getCoverImage().getFileSize(), session.getCoverImage().getFileType(), session.getCoverImage().getWidth(), session.getCoverImage().getHeight(), session.getFeeType().name(), session.getAmount().getAmount(), session.getMaxPersonnel(), session.getStatus().name());
    }

    @Override
    public Session findById(Long id) {
        String sql = "select id, start_date, end_date, file_size, file_type, width, height, fee_type, amount, max_personnel, status from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong(1),
                new SessionPeriod(rs.getDate(2).toLocalDate(), rs.getDate(3).toLocalDate()),
                new SessionCoverImage(rs.getLong(4), rs.getString(5), rs.getInt(6), rs.getInt(7)),
                SessionFeeType.valueOf(rs.getString(8)),
                new SessionAmount(rs.getInt(9)),
                rs.getInt(10),
                SessionStatus.valueOf(rs.getString(11)));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
