package nextstep.session.infrastructure;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.session.domain.CoverImage;
import nextstep.session.domain.ImageType;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionEnrollmentStatus;
import nextstep.session.domain.SessionProgressStatus;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionType;

@Repository("jdbcSessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String findSessionSql =
            "SELECT id, course_id, title, start_at, end_at, session_fee, student_capacity, "
                + "session_type, session_progress_status, session_enrollment_status "
                + "FROM session "
                + "WHERE id = ?";

        List<CoverImage> coverImages = findCoverImages(id);

        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
            rs.getLong("id"),
            rs.getLong("course_id"),
            rs.getString("title"),
            rs.getObject("start_at", LocalDate.class),
            rs.getObject("end_at", LocalDate.class),
            coverImages,
            SessionType.valueOf(rs.getString("session_type")),
            rs.getLong("student_capacity"),
            rs.getLong("session_fee"),
            SessionProgressStatus.valueOf(rs.getString("session_progress_status")),
            SessionEnrollmentStatus.valueOf(rs.getString("session_enrollment_status"))
        );

        return Optional.ofNullable(jdbcTemplate.queryForObject(findSessionSql, rowMapper, id));
    }

    private List<CoverImage> findCoverImages(Long sessionId) {
        String findCoverImagesSql =
            "SELECT size, width, height, image_type "
                + "FROM cover_image "
                + "WHERE session_id = ?";
        RowMapper<CoverImage> rowMapper = (rs, rowNum) -> new CoverImage(
            rs.getInt("size"),
            rs.getInt("width"),
            rs.getInt("height"),
            ImageType.valueOf(rs.getString("image_type"))
        );
        return jdbcTemplate.query(findCoverImagesSql, rowMapper, sessionId);
    }
}
