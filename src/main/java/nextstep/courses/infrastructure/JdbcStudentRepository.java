package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.StudentRepository;
import nextstep.courses.domain.session.entity.StudentEntity;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private final StudentRowMapper STUDENT_ROW_MAPPER = new StudentRowMapper();
    private final JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(NsUser student, long sessionId) {
        String sql = "insert into student (user_id, session_id) values(?, ?)";

        StudentEntity studentEntity = new StudentEntity(student.getId(), sessionId);

        return jdbcTemplate.update(sql,
                                   studentEntity.getUserId(),
                                   studentEntity.getSessionId());
    }

    @Override
    public List<StudentEntity> findBySessionId(long sessionId) {
        String sql = "select user_id, session_id from student where session_id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, STUDENT_ROW_MAPPER, sessionId)).orElse(new ArrayList<>());
    }

    private class StudentRowMapper implements RowMapper<StudentEntity> {

        @Override
        public StudentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new StudentEntity(
                rs.getLong("user_id"),
                rs.getLong("session_id"));
        }
    }
}
