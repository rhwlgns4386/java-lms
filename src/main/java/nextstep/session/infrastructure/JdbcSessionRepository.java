package nextstep.session.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.session.domain.CoverImage;
import nextstep.session.domain.FreeSessionPolicy;
import nextstep.session.domain.ImageType;
import nextstep.session.domain.PaidSessionPolicy;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPolicy;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionStatus;
import nextstep.session.domain.SessionType;

@Repository("jdbcSessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql =
            "SELECT s.id, s.session_id, s.title, s.start_at, s.end_at, s.session_fee, "
                + "s.session_type, s.session_status, c.size, c.width, c.height, c.image_type "
                + "FROM session s "
                + "INNER JOIN cover_image c "
                + "ON s.id = c.session_id "
                + "WHERE id = ?";

        RowMapper<Session> rowMapper = (rs, rowNum) -> {

            CoverImage coverImage = new CoverImage(
                rs.getInt("size"),
                rs.getInt("width"),
                rs.getInt("height"),
                ImageType.valueOf(rs.getString("image_type"))
            );

            SessionType sessionType = SessionType.valueOf(rs.getString("session_type"));

            return new Session(
                rs.getLong("id"),
                rs.getLong("session_id"),
                rs.getString("title"),
                rs.getObject("start_at", LocalDate.class),
                rs.getObject("end_at", LocalDate.class),
                coverImage,
                sessionType,
                getSessionPolicy(rs, sessionType),
                SessionStatus.valueOf(rs.getString("session_status"))
            );
        };

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private SessionPolicy getSessionPolicy(ResultSet rs, SessionType sessionType) throws SQLException {
        if (sessionType == SessionType.PAID) {
            int maxCapacity = rs.getInt("max_capacity");
            Long sessionFee = rs.getLong("session_fee");
            return new PaidSessionPolicy(maxCapacity, sessionFee);
        }
        return new FreeSessionPolicy();
    }
}
