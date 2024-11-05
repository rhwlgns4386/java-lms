package nextstep.courses.infrastructure.enrollment;

import nextstep.courses.entity.StudentEntity;
import nextstep.courses.type.EnrollmentState;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {

    private final static StudentRowMapper STUDENT_ROW_MAPPER = new StudentRowMapper();

    private final JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(StudentEntity studentEntity, long sessionId) {
        String sql = "insert into student "
                + "(ns_user_id, session_id, enrollment_state) "
                + "values(?, ?, ?)";

        return jdbcTemplate.update(sql,
                studentEntity.getStudent().getId(), sessionId, studentEntity.getEnrollmentState().name());
    }

    @Override
    public List<StudentEntity> findBySessionId(long sessionId) {
        String sql = "select "
                + "a.id as student_id, a.enrollment_state, a.session_id, b.id as ns_user_id, b.user_id "
                + "from student a inner join ns_user b on a.ns_user_id = b.id "
                + "where a.session_id = ?";
        return jdbcTemplate.query(sql, STUDENT_ROW_MAPPER, sessionId);
    }

    @Override
    public int update(StudentEntity student) {
        String sql = "update student set enrollment_state = ? where id = ?";
        return jdbcTemplate.update(sql, student.getEnrollmentState().name(), student.getId());
    }

    private static class StudentRowMapper implements RowMapper<StudentEntity> {

        @Override
        public StudentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            NsUser student = new NsUser(rs.getLong("ns_user_id"), rs.getString("user_id"));
            return new StudentEntity(
                    rs.getLong("student_id"),
                    EnrollmentState.valueOf(rs.getString("enrollment_state")),
                    student);
        }
    }
}
