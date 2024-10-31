package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.sessions.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, start_date, end_date, " +
                "type_code, maximum_number_of_applicants, fee_amount, " +
                "  status_code, course_id, created_at, updated_at from ns_session where id=?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong("id"),
                new SessionPeriod(rs.getString("start_date"),rs.getString("end_date")),
                new SessionStatus(SessionStatusEnum.getEnumByStatus(rs.getString("status_code"))),
                new SessionType(rs.getLong("fee_amount"),
                        rs.getInt("maximum_number_of_applicants")),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at")));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int save(Session session) {
        String sql = "insert into ns_session (start_date, end_date, status_code," +
                "type_code, maximum_number_of_applicants, fee_amount, course_id, created_at) values(?, ?, ?, ?, ?, ?, ?,?)";
        return jdbcTemplate.update(sql, session.getStartDate(), session.getEndDate(), session.getStatusCode(),
                session.getTypeCode(),session.getMaxNumberOfApplicants(), session.getFeeAmount(),session.getCourseId(),
                LocalDateTime.now());
    }

    @Override
    public int modifyStatus(Session session) {
        String sql = "update ns_session set status_code=? where id=?";
        return this.jdbcTemplate.update(sql, session.getStatusCode(), session.getId());
    }

    @Override
    public int modifyPeriod(Session session) {
        String sql = "update ns_session set start_date=?, end_date=? where id=?";
        return this.jdbcTemplate.update(sql, session.getStartDate(), session.getEndDate(), session.getId());
    }

    @Override
    public int modifyType(Session session) {
        String sql = "update ns_session set type_code=?, maximum_number_of_applicants=? , fee_amount where id=?";
        return this.jdbcTemplate.update(sql, session.getTypeCode(), session.getMaxNumberOfApplicants(), session.getFeeAmount(), session.getId());
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
