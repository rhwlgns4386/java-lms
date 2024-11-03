package nextstep.courses.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.courses.domain.RegisterStatus;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.Type;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql =
            "insert into session (course_id, teacher_id, price, session_status, register_status, max_student_size, start_at, end_at, type) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            session.getCourseId(),
            session.getTeacherId(),
            session.getPrice(),
            session.getSessionStatus().toString(),
            session.getRegisterStatus().toString(),
            session.getMaxStudentSize(),
            session.getSessionDate().getStart(),
            session.getSessionDate().getEnd(),
            session.getSessionType().toString());
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, course_id, teacher_id, price, session_status, register_status, max_student_size, start_at, end_at, type from session where id = ?";

        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
            rs.getLong(1),
            rs.getLong(2),
            rs.getLong(3),
            rs.getLong(4),
            SessionStatus.from(rs.getString(5)),
            RegisterStatus.from(rs.getString(6)),
            rs.getInt(7),
            new SessionDate(
                toLocalDateTime(rs.getTimestamp(8)),
                toLocalDateTime(rs.getTimestamp(9))
            ),
            Type.from(rs.getString(10))
        );

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public List<Session> findByTeacherId(Long teacherId) {
        String sql = "select id, course_id, teacher_id, price, session_status, register_status, max_student_size, start_at, end_at, type from session where teacher_id = ?";

        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
            rs.getLong(1),
            rs.getLong(2),
            rs.getLong(3),
            rs.getLong(4),
            SessionStatus.from(rs.getString(5)),
            RegisterStatus.from(rs.getString(6)),
            rs.getInt(7),
            new SessionDate(
                toLocalDateTime(rs.getTimestamp(8)),
                toLocalDateTime(rs.getTimestamp(9))
            ),
            Type.from(rs.getString(10))
        );

        return jdbcTemplate.query(sql, rowMapper, teacherId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
