package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JdbcCourseRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcCourseRepository jdbcCourseRepository;

    private Course course;
    private final String INSERT_COURSE_SQL = "INSERT INTO courses (id, title, creator_id, cohort, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SELECT_COURSE_BY_ID_SQL = "SELECT * FROM courses WHERE id = ?";

    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, row) -> new Course(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getLong("creator_id"),
            rs.getInt("cohort"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course(1L, "자바지기", 1L, 1, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void 코스를_저장한다() {
        when(jdbcTemplate.update(INSERT_COURSE_SQL,
                course.getId(),
                course.getTitle(),
                course.getCreatorId(),
                course.getCohort(),
                course.getCreatedAt(),
                course.getCreatedAt())).thenReturn(1);

        int result = jdbcCourseRepository.save(course);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void 코스를_조회한다() {
        Long courseId = 1L;
        when(jdbcTemplate.queryForObject(eq(SELECT_COURSE_BY_ID_SQL), (RowMapper<Object>) any(), eq(courseId))).thenReturn(course);

        Optional<Course> optionalCourse = jdbcCourseRepository.findById(courseId);

        assertThat(optionalCourse).isPresent();
        Course foundCourse = optionalCourse.get();

        assertThat(foundCourse).isEqualTo(course);
        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getId()).isEqualTo(course.getId());
        assertThat(foundCourse.getTitle()).isEqualTo(course.getTitle());
        assertThat(foundCourse.getCreatorId()).isEqualTo(course.getCreatorId());
        assertThat(foundCourse.getCohort()).isEqualTo(course.getCohort());
    }

    @Test
    public void ID에_맞는_코스가_없으면_null을_반환한다() {
        Long courseId = 2L;
        when(jdbcTemplate.queryForObject(eq(SELECT_COURSE_BY_ID_SQL), eq(COURSE_ROW_MAPPER), eq(courseId))).thenReturn(course);

        Optional<Course> optionalCourse = jdbcCourseRepository.findById(courseId);
        Course foundCourse = optionalCourse.orElse(null);

        assertThat(foundCourse).isNull();
    }
}

