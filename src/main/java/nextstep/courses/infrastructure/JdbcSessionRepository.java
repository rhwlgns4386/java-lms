package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.port.SessionRepository;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPriceType;
import nextstep.courses.domain.session.SessionStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Objects;

@Repository("jdbcSessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Session session) {
        String sql = "insert into session(price, session_price_type, session_status, start_date_time, end_date_time) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, session.getSessionPrice());
            ps.setString(2, session.getSessionPriceType().toString());
            ps.setString(3, session.getSessionStatus().toString());
            ps.setTimestamp(4, Timestamp.valueOf(session.getStartDateTime()));
            ps.setTimestamp(5, Timestamp.valueOf(session.getEndDateTime()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Session findById(Long id) {

        String sql = "select s.id, s.price, s.session_price_type, s.session_status, s.start_date_time, s.end_date_time, sci.file_path, sci.height, sci.width from session s inner join session_cover_image sci on s.id = sci.session_id where s.id = ?";

        RowMapper<Session> rowMapper = ((rs, rowNum) -> new Session.SessionBuilder()
                .sessionId(rs.getLong(1))
                .price(rs.getLong(2))
                .sessionPriceType(SessionPriceType.valueOf(rs.getString(3)))
                .sessionStatus(SessionStatus.valueOf(rs.getString(4)))
                .startDateTime(rs.getTimestamp(5).toLocalDateTime())
                .endDateTime(rs.getTimestamp(6).toLocalDateTime())
                .sessionCoverImage(new SessionCoverImage.SessionCoverImageBuilder().fileName(rs.getString(7)).height(rs.getInt(8)).width(rs.getInt(9)).build())
                .build()
        );

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
