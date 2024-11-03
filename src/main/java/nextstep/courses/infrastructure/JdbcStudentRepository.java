package nextstep.courses.infrastructure;

import nextstep.courses.domain.student.Student;
import nextstep.courses.domain.student.StudentRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStudentRepository implements StudentRepository {
    private static final RowMapper<Student> STUDENT_ROW_MAPPER = (rs, rowNum) -> new Student(
            rs.getLong(2),
            rs.getLong(1)
    );
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    public JdbcStudentRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int save(Student student, Long sessionId) {
        String sql = "insert into student (ns_user_id, session_id, amount) values(:nsUserId,:sessionId,:amount)";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("nsUserId", student.getNsUserId());
        param.addValue("sessionId", sessionId);
        param.addValue("amount", student.getAmount());
        return namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public int[] saveAll(List<Student> students, Long sessionId) {
        String sql = "insert into student (ns_user_id, session_id, amount) values(:nsUserId,:sessionId,:amount)";
        MapSqlParameterSource[] batch = students.stream()
                .map(student -> {
                    MapSqlParameterSource param = new MapSqlParameterSource();
                    param.addValue("nsUserId", student.getNsUserId());
                    param.addValue("sessionId", sessionId);
                    param.addValue("amount", student.getAmount());
                    return param;
                })
                .toArray(MapSqlParameterSource[]::new);
        return namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }

    @Override
    public Optional<Student> findById(Long nsUserId, Long sessionId) {
        String sql = "select ns_user_id, amount from student where ns_user_id = :nsUserId and session_id = :sessionId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("nsUserId", nsUserId);
        param.addValue("sessionId", sessionId);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, param, STUDENT_ROW_MAPPER));
    }

    @Override
    public List<Student> findAllBySessionId(Long sessionId) {
        String sql = "select ns_user_id, amount from student where session_id = :sessionId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("sessionId", sessionId);
        return namedParameterJdbcTemplate.query(sql, param, STUDENT_ROW_MAPPER);
    }
}
