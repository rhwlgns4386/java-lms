package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseDate;
import nextstep.courses.domain.CourseDetail;
import nextstep.courses.domain.CourseRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcCourseRepository implements CourseRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String INSERT_COURSE_SQL = "INSERT INTO courses (id, title, creator_id, cohort, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_COURSE_BY_ID_SQL = "SELECT * FROM courses WHERE id = ?";


    @Override
    public int save(Course course) {
        return jdbcTemplate.update(
                INSERT_COURSE_SQL,
                course.getId(),
                course.getTitle(),
                course.getCreatorId(),
                course.getCohort(),
                course.getCreatedAt(),
                course.getCreatedAt()
        );
    }

    @Override
    public Optional<Course> findById(Long id) {
        try {
            Course course = jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID_SQL, COURSE_ROW_MAPPER, id);
            return Optional.ofNullable(course);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, row) -> new Course(
            rs.getLong("id"),
            new CourseDetail(
                    rs.getString("title"),
                    rs.getLong("creator_id"),
                    rs.getInt("cohort")
            ),
            new CourseDate(
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime())
    );

}
