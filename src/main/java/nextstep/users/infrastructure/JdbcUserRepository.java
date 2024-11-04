package nextstep.users.infrastructure;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import nextstep.users.domain.UserType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository("userRepository")
public class JdbcUserRepository implements UserRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcUserRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<NsUser> findByUserId(String userId) {
        String sql = "select id, user_id, type, password, name, email, created_at, updated_at from ns_user where user_id = ?";
        RowMapper<NsUser> rowMapper = (rs, rowNum) -> new NsUser(
                rs.getLong(1),
                rs.getString(2),
                UserType.of(rs.getString(3)),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                toLocalDateTime(rs.getTimestamp(7)),
                toLocalDateTime(rs.getTimestamp(8)));
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, userId));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
