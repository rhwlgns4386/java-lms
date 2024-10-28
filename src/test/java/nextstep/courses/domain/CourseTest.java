package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CourseTest {
    @Test
    @DisplayName("Course class 생성")
    void createCourseTest() {
        Long id = 1L;
        Long order = 1L;
        String title = "TDD with Java";
        Long creatorId = 1L;

        Course course = new Course(id, order, title, creatorId);

        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Course에 Session 등록")
    void addSessionInCourseTest() {
        Long id = 1L;
        Long order = 1L;
        String title = "TDD with Java";
        Long creatorId = 1L;

        Course course = new Course(id, order, title, creatorId);

        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImageProperty(300L, 200L));
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);
        SessionId sessionId = SessionId.of(1L, "TDD");

        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        course.registerSession(SessionType.FREE, sessionDate, image, sessionId, null, null);

        SessionManager sessionManager = course.getSessionManager();
        Assertions.assertThat(sessionManager.findBySessionId(sessionId).getSessionId()).isEqualTo(freeSession.getSessionId());
    }

}
