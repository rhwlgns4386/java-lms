package nextstep.courses.infrastructure;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;
    private SessionEntityMapper sessionEntityMapper = new SessionEntityMapper();

    public JdbcSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, charge, capacity, sessionStatus, image_file_name, image_width, image_height,"
                + "image_size, imageType, start_date, end_date from session where id = ?";
        Set<EnrollmentStudent> enrollmentStudents = findAllEnrollmentStudentBySessionId(id);
        RowMapper<Session> rowMapper = ((rs, rowNum) -> sessionEntityMapper.toEntity(
                rs.getLong(1),
                rs.getInt(2),
                (Integer) rs.getObject(3),
                enrollmentStudents,
                SessionStatus.findByName(rs.getString(4)),
                rs.getString(5),
                rs.getInt(6),
                rs.getInt(7),
                rs.getInt(8),
                ImageType.findByName(rs.getString(9)),
                toLocalDate(rs.getDate(10)),
                toLocalDate(rs.getDate(11))
        ));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private Set<EnrollmentStudent> findAllEnrollmentStudentBySessionId(Long id) {

        String sql = "SELECT session_id, user_id FROM enrollment_students where session_id = ?";

        RowMapper<EnrollmentStudent> rowMapper = (rs, rowNum) -> {
            long sessionId = rs.getLong("session_id");
            long userId = rs.getLong("user_id");
            return new EnrollmentStudent(sessionId, userId);
        };

        return new HashSet<>(jdbcTemplate.query(sql, rowMapper, id));
    }

    private LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }

    @Override
    public void update(Session session) {
        EnrollmentInsertCacheProxy enrollments = toProxy(session.getEnrollments());
        Set<EnrollmentStudent> enrollmentStudents = enrollments.insertEnrollmentStudents();
        String sql = "insert into enrollment_students (session_id, user_id) values(?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                EnrollmentStudent enrollmentStudent = (EnrollmentStudent) enrollmentStudents.toArray()[i];
                ps.setLong(1, enrollmentStudent.getSessionId());
                ps.setLong(2, enrollmentStudent.getUserId());
            }

            @Override
            public int getBatchSize() {
                return enrollmentStudents.size();
            }
        });
    }

    private EnrollmentInsertCacheProxy toProxy(Enrollments enrollments) {
        if (enrollments instanceof EnrollmentInsertCacheProxy) {
            return (EnrollmentInsertCacheProxy) enrollments;
        }
        throw new IllegalStateException("업데이트를 진행 할 수 없습니다");
    }
}
