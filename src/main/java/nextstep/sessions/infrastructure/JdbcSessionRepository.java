package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.sessions.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select s.id, s.start_date,s.end_date, s.status_code , s.progress_status_code, s.recruitment_status_code, " +
                "s.fee_amount, s.maximum_number_of_applicants, " +
                " s.created_at ,s.updated_at , c.id as courseId, c.title, c.creator_id, c.created_at as courseCreated , c.updated_at as courseUpdated " +
                " from ns_session as s left outer join course as c " +
                "on s.course_id = c.id " +
                "where s.id=?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> {
            Session session = new Session(
                    rs.getLong("id"),
                    new SessionPeriod(rs.getString("start_date"),rs.getString("end_date")),
                    new SessionStatus(rs.getString("status_code"),
                            rs.getString("progress_status_code"),
                            rs.getString("recruitment_status_code")),
                    new SessionType(rs.getLong("fee_amount"),
                            rs.getInt("maximum_number_of_applicants")),
                    toLocalDateTime(rs.getTimestamp("created_at")),
                    toLocalDateTime(rs.getTimestamp("updated_at")));
            Course relatedCourse = new Course(
                    rs.getLong("courseId"),
                    rs.getString("title"),
                    rs.getLong("creator_id"),
                    toLocalDateTime(rs.getTimestamp("courseCreated")),
                    toLocalDateTime(rs.getTimestamp("courseUpdated"))
            );
            session.toCourse(relatedCourse);
            return session;

        };
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public List<Session> findByCourseId(Long courseId) {
        String sql = "select id, start_date, end_date, " +
                "type_code, maximum_number_of_applicants, fee_amount, " +
                "  status_code, progress_status_code, recruitment_status_code, course_id, created_at, updated_at from ns_session where course_id=?";

        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong("id"),
                new SessionPeriod(rs.getString("start_date"),rs.getString("end_date")),
                new SessionStatus(rs.getString("status_code"),
                        rs.getString("progress_status_code"),
                        rs.getString("recruitment_status_code")),
                new SessionType(rs.getLong("fee_amount"),
                        rs.getInt("maximum_number_of_applicants")),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at")));
        return this.jdbcTemplate.query(sql, rowMapper, courseId);
    }

    @Override
    public int save(Session session) {
        String sql = "insert into ns_session (start_date, end_date, status_code, progress_status_code, " +
                "recruitment_status_code, " +
                "type_code, maximum_number_of_applicants, fee_amount, course_id, created_at) " +
                "values(?, ?, ?, ?, ?, ?, ?,?,?,?)";
        return jdbcTemplate.update(sql, session.getStartDate(), session.getEndDate(), session.getStatusCode(),
                session.getSessionStatus().getProgressStatus().getStatusCode(), session.getSessionStatus().getRecruitmentStatus().getStatusCode(),
                session.getTypeCode(),session.getMaxNumberOfApplicants(), session.getFeeAmount(),session.getCourseId(),
                LocalDateTime.now());
    }

    @Override
    public int modifyStatus(Session session) {
        String sql = "update ns_session set status_code=?, progress_status_code = ? ,recruitment_status_code =? " +
                " where id=?";
        return this.jdbcTemplate.update(sql, session.getSessionStatus().getStatus(),session.getSessionStatus().getProgressStatus().getStatusCode(),
                session.getSessionStatus().getRecruitmentStatus().getStatusCode(), session.getId());
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
