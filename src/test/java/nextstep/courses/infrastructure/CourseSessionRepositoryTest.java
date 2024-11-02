package nextstep.courses.infrastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CourseSessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private JdbcCourseSessionRepository courseSessionRepository;
    private Long courseId;
    private Long sessionId1;
    private Long sessionId2;

    @BeforeEach
    void setUp() {
        courseSessionRepository = new JdbcCourseSessionRepository(jdbcTemplate);

        courseId = 1L;
        sessionId1 = 101L;
        sessionId2 = 102L;

        jdbcTemplate.update("INSERT INTO cover_image (file_size, image_type, width, height, created_at) VALUES (102400, 'JPG', 600, 400, CURRENT_TIMESTAMP)");
        jdbcTemplate.update("INSERT INTO course (id, title, creator_id, created_at) VALUES (?, 'Test Course', 1, CURRENT_TIMESTAMP)", courseId);
        jdbcTemplate.update("INSERT INTO session (id, status, start_date, end_date, cover_image_id, session_type) VALUES (?, 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'FREE')", sessionId1);
        jdbcTemplate.update("INSERT INTO session (id, status, start_date, end_date, cover_image_id, session_type) VALUES (?, 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'FREE')", sessionId2);
    }

    @DisplayName("Course에 Session을 추가하고 조회할 수 있다.")
    @Test
    void addAndFindSessionsForCourse() {
        // given
        courseSessionRepository.addSessionToCourse(courseId, sessionId1);
        courseSessionRepository.addSessionToCourse(courseId, sessionId2);

        // when
        List<Long> sessionIds = courseSessionRepository.findSessionIdsByCourseId(courseId);

        // then
        assertThat(sessionIds).containsExactlyInAnyOrder(sessionId1, sessionId2);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM course_session");
        jdbcTemplate.update("DELETE FROM session");
        jdbcTemplate.update("DELETE FROM course");
    }
}
