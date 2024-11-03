package nextstep.courses.infrastructure;

import nextstep.courses.domain.enroll.EnrollUserInfo;
import nextstep.courses.domain.port.EnrollUserInfoRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("jdbcEnrollUserInfoRepository")
public class JdbcEnrollUserInfoRepository implements EnrollUserInfoRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcEnrollUserInfoRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(EnrollUserInfo enrollUserInfo) {
        String sql = "insert into enroll_user_info (session_id, user_id) values(?,?)";
        return jdbcTemplate.update(sql, enrollUserInfo.getSessionId(), enrollUserInfo.getNsUserId());
    }

    @Override
    public EnrollUserInfo findBySessionIdAndNsUserId(Long sessionId, Long userId) {
        String sql = "select session_id, user_id from enroll_user_info where session_id = ? and user_id = ?";
        RowMapper<EnrollUserInfo> rowMapper = (rs, rowNum) -> new EnrollUserInfo(
                rs.getLong(1),
                rs.getLong(2)
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, sessionId, userId);
    }
}
