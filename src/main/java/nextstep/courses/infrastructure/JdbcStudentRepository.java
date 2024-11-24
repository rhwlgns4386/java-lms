package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.StudentRepository;
import nextstep.courses.domain.session.enrollment.ApprovalStatus;
import nextstep.courses.domain.session.entity.StudentEntity;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private final StudentRowMapper STUDENT_ROW_MAPPER = new StudentRowMapper();
    private final JdbcOperations jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public int save(NsUser student, long sessionId) {
        String sql = "insert into student (user_id, session_id, approval_status) values(?, ?, ?)";

        StudentEntity studentEntity = new StudentEntity(student.getId(), sessionId);

        return jdbcTemplate.update(sql,
                                   studentEntity.getUserId(),
                                   studentEntity.getSessionId(),
                                   studentEntity.getApprovalStatus());
    }

    @Override
    public List<StudentEntity> findBySessionId(long sessionId) {
        String sql = "select user_id, session_id, approval_status from student where session_id = ?";

        return Optional.ofNullable(jdbcTemplate.query(sql, STUDENT_ROW_MAPPER, sessionId)).orElse(new ArrayList<>());
    }

    @Override
    public List<StudentEntity> findByIdAndSessionId(long sessionId, List<Long> userIds) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("sessionId", sessionId);
        parameterMap.put("userIds", userIds);

        String sql = "select user_id, session_id, approval_status from student where session_id = :sessionId and user_id in (:userIds)";

        return Optional.ofNullable(namedParameterJdbcTemplate.query(sql, parameterMap, STUDENT_ROW_MAPPER))
                       .filter(data -> !data.isEmpty())
                       .orElseThrow(NotFoundException::new);
    }

    @Override
    public int updateApproved(long sessionId, List<Long> userIds) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("sessionId", sessionId);
        parameterMap.put("userIds", userIds);
        parameterMap.put("status", ApprovalStatus.APPROVED.name());

        String sql = "update student set approval_status = :status where session_id = :sessionId and user_id in (:userIds)";

        return namedParameterJdbcTemplate.update(sql, parameterMap);
    }

    @Override
    public int updateDisapproved(long sessionId, long userId) {
        findByIdAndSessionId(sessionId, List.of(userId));

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("sessionId", sessionId);
        parameterMap.put("userId", userId);
        parameterMap.put("status", ApprovalStatus.DISAPPROVED.name());

        String sql = "update student set approval_status = :status where session_id = :sessionId and user_id = :userId";

        return namedParameterJdbcTemplate.update(sql, parameterMap);
    }

    private class StudentRowMapper implements RowMapper<StudentEntity> {

        @Override
        public StudentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new StudentEntity(
                rs.getLong("user_id"),
                rs.getLong("session_id"),
                rs.getString("approval_status"));
        }
    }
}
