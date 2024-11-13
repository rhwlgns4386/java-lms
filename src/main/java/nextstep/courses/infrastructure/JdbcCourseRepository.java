package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCourseRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int save(Course course) {
        String sql = "insert into course (title, creator_id, cardinal_number, created_at) values(:title, :creatorId, :cardinalNumber, :createdAt)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", course.getTitle())
                .addValue("cardinalNumber", course.getCardinalNumber())
                .addValue("creatorId", course.getCreatorId())
                .addValue("createdAt", course.getCreatedAt());
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Course> findById(Long id) {
        String sql = "select id, title, cardinal_number, creator_id, created_at, updated_at from course where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, COURSE_ROW_MAPPER));
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
