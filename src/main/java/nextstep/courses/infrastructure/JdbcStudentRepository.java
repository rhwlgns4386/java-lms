package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionApprovalStatus;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    public static final RowMapper<Student> STUDENT_ROW_MAPPER = (rs, rowNum) -> new Student(
            rs.getLong("id"),
            rs.getLong("ns_user_id"),
            rs.getLong("session_id"),
            SessionApprovalStatus.valueOf(rs.getString("approval_status")));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcStudentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long save(Student student) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nsUserId", student.getNsUserId())
                .addValue("sessionId", student.getSessionId())
                .addValue("approvalStatus", student.getApprovalStatus().name());
        if (student.getId() == null || student.getId() == 0) {
            String insertSql = "insert into student (ns_user_id, session_id, approval_status) values (:nsUserId, :sessionId, :approvalStatus)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(insertSql, params, keyHolder, new String[]{"id"});
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
        params.addValue("id", student.getId());
        String updateSql = "update student set ns_user_id = :nsUserId, session_id = :sessionId, approval_status = :approvalStatus where id = :id";
        namedParameterJdbcTemplate.update(updateSql, params);
        return student.getId();
    }

    @Override
    public Optional<Student> findById(Long id) {
        String sql = "select id, ns_user_id, session_id, approval_status from student where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, STUDENT_ROW_MAPPER));
    }

    @Override
    public List<Student> findBySessionId(Long sessionId) {
        String sql = "select id, ns_user_id, session_id, approval_status from student where session_id = :sessionId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("sessionId", sessionId);
        return namedParameterJdbcTemplate.query(sql, params, STUDENT_ROW_MAPPER);
    }
}
