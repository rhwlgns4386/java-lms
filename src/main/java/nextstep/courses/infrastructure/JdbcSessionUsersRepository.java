package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStudent;
import nextstep.courses.domain.SessionStudents;
import nextstep.courses.domain.SessionUsersRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class JdbcSessionUsersRepository implements SessionUsersRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionUsersRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(SessionStudent student) {
        String sql = "insert into session_users (session_id, ns_user_id) values(?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"}); // "id"는 자동 생성되는 열 이름
            ps.setLong(1, student.getSessionId());
            ps.setLong(2, student.getNsUserId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public SessionStudent findById(Long id) {
        String sql = "select id, session_id, ns_user_id, is_approved from session_users where id = ?";

        RowMapper<SessionStudent> rowMapper = (rs, rowNum) -> new SessionStudent(
                rs.getLong("id"),
                rs.getLong("session_id"),
                rs.getLong("ns_user_id"),
                rs.getBoolean("is_approved")
        );

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int updateBySelected(Long id, boolean isSelected) {
        String sql = "update session_users set is_approved = ? where id = ?";
        return jdbcTemplate.update(sql, isSelected, id);
    }

    @Override
    public SessionStudents findBySessionId(Long sessionId) {
        String sql = "select id, session_id, ns_user_id, is_approved from session_users where session_id = ?";
        RowMapper<SessionStudent> rowMapper = (rs, rowNum) -> new SessionStudent(
                rs.getLong("id"),
                rs.getLong("session_id"),
                rs.getLong("ns_user_id"),
                rs.getBoolean("is_approved")
        );

        List<SessionStudent> students = jdbcTemplate.query(sql, rowMapper, sessionId);
        return new SessionStudents(students);
    }

    @Override
    public void deleteBySessionId(Long sessionId) {
        String sql = "delete from session_users where session_id = ?";
        jdbcTemplate.update(sql, sessionId);
    }

    @Override
    public int[] bulkSave(List<SessionStudent> students) {
        String sql = "insert into session_users (session_id, ns_user_id) VALUES (?, ?)";

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                SessionStudent student = students.get(index);
                ps.setLong(1, student.getSessionId());
                ps.setLong(2, student.getNsUserId());
            }

            @Override
            public int getBatchSize() {
                return students.size();
            }
        });
    }

}
