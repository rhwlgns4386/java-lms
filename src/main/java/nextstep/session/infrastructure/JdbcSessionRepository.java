package nextstep.session.infrastructure;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.session.domain.CoverImage;
import nextstep.session.domain.ImageType;
import nextstep.session.domain.Session;
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
            "SELECT s.id, s.course_id, s.title, s.start_at, s.end_at, s.session_fee, s.student_capacity, "
                + "s.session_type, s.session_status, c.size, c.width, c.height, c.image_type "
                + "FROM session s "
                + "INNER JOIN cover_image c "
                + "ON s.id = c.session_id "
                + "WHERE s.id = ?";

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
                rs.getLong("course_id"),
                rs.getString("title"),
                rs.getObject("start_at", LocalDate.class),
                rs.getObject("end_at", LocalDate.class),
                coverImage,
                sessionType,
                rs.getLong("student_capacity"),
                rs.getLong("session_fee"),
                SessionStatus.valueOf(rs.getString("session_status"))
            );
        };

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }
}
