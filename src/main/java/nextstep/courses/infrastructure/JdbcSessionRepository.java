package nextstep.courses.infrastructure;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;


public abstract class JdbcSessionRepository<T extends Session> implements SessionRepository<T> {
    protected final JdbcOperations jdbcTemplate;

    protected JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveBaseSession(T session, Long courseId) {
        String sql = "INSERT INTO session (title, session_start, session_end, image_size, image_width, image_height, image_type, session_status, course_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, session.getTitle());
            ps.setDate(2, java.sql.Date.valueOf(session.getSessionStart()));
            ps.setDate(3, java.sql.Date.valueOf(session.getSessionEnd()));
            ps.setLong(4, session.getImageSize());
            ps.setLong(5, session.getImageWidth());
            ps.setLong(6, session.getImageHeight());
            ps.setString(7, session.getImageType());
            ps.setString(8, session.getSessionStatus() != null ? session.getSessionStatus().name() : null);
            ps.setLong(9, courseId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

}
