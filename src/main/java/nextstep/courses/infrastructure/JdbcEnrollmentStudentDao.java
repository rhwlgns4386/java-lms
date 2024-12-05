package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.RequestStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcEnrollmentStudentDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcEnrollmentStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Set<EnrollmentStudent> findAllEnrollmentStudentBySessionId(Long id) {
        String sql = "SELECT session_id, user_id,request_status FROM enrollment_students where session_id = ?";

        RowMapper<EnrollmentStudent> rowMapper = (rs, rowNum) -> {
            long sessionId = rs.getLong("session_id");
            long userId = rs.getLong("user_id");
            String requestStatus = rs.getString("request_status");
            return new EnrollmentStudent(sessionId, userId, RequestStatus.findByName(requestStatus));
        };

        return new HashSet<>(jdbcTemplate.query(sql, rowMapper, id));
    }

    public Set<EnrollmentStudent> findAllEnrollmentStudentBySessionIdAndRequestStatus(long id,
                                                                                      RequestStatus requestStatus) {
        String sql = "SELECT session_id, user_id,request_status FROM enrollment_students where session_id = ? and request_status = ?";

        RowMapper<EnrollmentStudent> rowMapper = (rs, rowNum) -> {
            long sessionId = rs.getLong("session_id");
            long userId = rs.getLong("user_id");
            return new EnrollmentStudent(sessionId, userId, requestStatus);
        };

        return new HashSet<>(jdbcTemplate.query(sql, rowMapper, id, requestStatus.name()));
    }

    void insertItem(Set<EnrollmentStudent> enrollmentStudents) {
        String sql = "insert into enrollment_students (session_id, user_id,request_status) values(?, ?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                EnrollmentStudent enrollmentStudent = (EnrollmentStudent) enrollmentStudents.toArray()[i];
                ps.setLong(1, enrollmentStudent.getSessionId());
                ps.setLong(2, enrollmentStudent.getUserId());
                ps.setString(3, enrollmentStudent.getRequestStatusString());
            }

            @Override
            public int getBatchSize() {
                return enrollmentStudents.size();
            }
        });
    }

    public void deleteAllBySessionId(Set<EnrollmentStudent> enrollmentStudents) {
        for (EnrollmentStudent student : enrollmentStudents) {
            long sessionId = student.getSessionId();
            long userId = student.getUserId();

            // SQL DELETE 쿼리 작성
            String sql = "DELETE FROM enrollment_students WHERE session_id = ? and user_id =?";

            // JdbcTemplate을 사용하여 쿼리 실행
            jdbcTemplate.update(sql, sessionId, userId);
        }
    }
}
