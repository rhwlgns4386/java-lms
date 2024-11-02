package nextstep.courses.infrastructure;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.course.CourseRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Repository("courseRepository")
public class JdbcCourseRepository implements CourseRepository {
    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) -> new Course(
            rs.getLong(1),
            rs.getLong(2),
            rs.getString(3),
            rs.getLong(4),
            toLocalDateTime(rs.getTimestamp(5)),
            toLocalDateTime(rs.getTimestamp(6)));

    private JdbcOperations jdbcTemplate;
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    public JdbcCourseRepository(JdbcOperations jdbcTemplate,
                                NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long save(Course course) {
        String sql = "insert into course (title, \"order\", creator_id, created_at) values(:title, :order, :creatorId, :createdAt)";

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("title", course.getTitle());
        param.addValue("order", course.getOrder());
        param.addValue("creatorId", course.getCreatorId());
        param.addValue("createdAt", course.getCreatedAt());
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, param, generatedKeyHolder);

        return Objects.requireNonNull(generatedKeyHolder.getKey()).longValue();
    }

    @Override
    public Course findById(Long id) {
        String sql = "select id, \"order\", title, creator_id, created_at, updated_at from course where id = :id";

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);

        return namedParameterJdbcTemplate.queryForObject(sql, param, COURSE_ROW_MAPPER);
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
