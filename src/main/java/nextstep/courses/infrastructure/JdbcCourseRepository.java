package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository("courseRepository")
public class JdbcCourseRepository implements CourseRepository {
    public static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) -> new Course(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getLong("cardinal_number"),
            rs.getInt("creator_id"),
            toLocalDateTime(rs.getTimestamp("created_at")),
            toLocalDateTime(rs.getTimestamp("updated_at")));

    private JdbcOperations jdbcTemplate;

    public JdbcCourseRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Course course) {
        String sql = "insert into course (title, creator_id, cardinal_number, created_at) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, course.getTitle(), course.getCreatorId(), course.getCardinalNumber(), course.getCreatedAt());
    }

    @Override
    public Optional<Course> findById(Long id) {
        String sql = "select id, title, cardinal_number, creator_id, created_at, updated_at from course where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, COURSE_ROW_MAPPER, id));
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
