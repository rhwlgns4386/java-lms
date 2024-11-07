package nextstep.courses.infrastructure;

import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    public static final RowMapper<Student> STUDENT_ROW_MAPPER = (rs, rowNum) -> new Student(
            rs.getLong("id"),
            rs.getLong("ns_user_id"),
            rs.getLong("session_id"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcStudentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String sql = "insert into student (ns_user_id, session_id) values (:nsUserId, :sessionId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nsUserId", student.getNsUserId())
                .addValue("sessionId", student.getSessionId());
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Student> findById(Long id) {
        String sql = "select id, ns_user_id, session_id from student where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, STUDENT_ROW_MAPPER));
    }

    @Override
    public List<Student> findBySessionId(Long sessionId) {
        String sql = "select id, ns_user_id, session_id from student where session_id = :sessionId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("sessionId", sessionId);
        return namedParameterJdbcTemplate.query(sql, params, STUDENT_ROW_MAPPER);
    }
}
