package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.Session;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository("courseRepository")
public class JdbcCourseRepository implements CourseRepository {
    private final JdbcOperations jdbcTemplate;
    private final CourseSessionRepository courseSessionRepository;
    private final SessionRepository sessionRepository;

    public JdbcCourseRepository(JdbcOperations jdbcTemplate, CourseSessionRepository courseSessionRepository, SessionRepository sessionRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseSessionRepository = courseSessionRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public int save(Course course) {
        String sql = "insert into course (title, creator_id, created_at) values(?, ?, ?)";
        return jdbcTemplate.update(sql, course.getTitle(), course.getCreatorId(), course.getCreatedAt());
    }

    @Override
    public Course findById(Long id) {
        String sql = "select id, title, creator_id, created_at, updated_at from course where id = ?";
        List<Session> sessions = getSessions(id);
        RowMapper<Course> rowMapper = (rs, rowNum) -> new Course(
                rs.getLong(1),
                rs.getString(2),
                rs.getLong(3),
                sessions,
                toLocalDateTime(rs.getTimestamp(4)),
                toLocalDateTime(rs.getTimestamp(5)));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private List<Session> getSessions(Long id) {
        List<Long> sessionIds = courseSessionRepository.findByCourseId(id);
        return sessionIds.stream()
                .map(sessionRepository::findById)
                .collect(Collectors.toList());
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
