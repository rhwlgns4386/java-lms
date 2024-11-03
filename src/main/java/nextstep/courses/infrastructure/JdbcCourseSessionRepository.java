package nextstep.courses.infrastructure;

import nextstep.courses.domain.course.CourseSessionRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionRegistrationRepository")
public class JdbcCourseSessionRepository implements CourseSessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcCourseSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSessionToCourse(Long courseId, Long sessionId) {
        String sql = "INSERT INTO course_session (course_id, session_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, courseId, sessionId);
    }

    @Override
    public List<Long> findSessionIdsByCourseId(Long courseId) {
        String sql = "SELECT session_id FROM course_session WHERE course_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, courseId);
    }
}
