package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcEnrollmentStudentDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcEnrollmentStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Set<EnrollmentStudent> findAllEnrollmentStudentBySessionId(Long id) {
        String sql = "SELECT session_id, user_id FROM enrollment_students where session_id = ?";

        RowMapper<EnrollmentStudent> rowMapper = (rs, rowNum) -> {
            long sessionId = rs.getLong("session_id");
            long userId = rs.getLong("user_id");
            return new EnrollmentStudent(sessionId, userId);
        };

        return new HashSet<>(jdbcTemplate.query(sql, rowMapper, id));
    }

    void insertItem(Set<EnrollmentStudent> enrollmentStudents) {
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
}
