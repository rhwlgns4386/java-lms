package nextstep.courses.infrastructure;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.course.CourseRepository;
import nextstep.courses.domain.course.CourseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Repository("courseRepository")
public class JdbcCourseRepository implements CourseRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCourseRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Course course) {
        CourseEntity courseEntity = CourseEntity.from(course);
        String sql = "insert into course (title, creator_id, created_at) values (?, ?, ?)";
        return jdbcTemplate.update(sql,
                courseEntity.getTitle(),
                courseEntity.getCreatorId(),
                courseEntity.getCreatedAt()
        );
    }

    @Override
    public Course findById(Long id) {
        String sql = "select id, title, creator_id, created_at, updated_at from course where id = ?";
        RowMapper<CourseEntity> rowMapper = (rs, rowNum) -> new CourseEntity(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getLong("creator_id"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );
        CourseEntity courseEntity = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return Objects.requireNonNull(courseEntity).toCourse();
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
