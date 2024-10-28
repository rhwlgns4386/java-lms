package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
    @DisplayName("Course에 Session 추가")
    void addSessionInCourseTest() {
        Long id = 1L;
        Long order = 1L;
        String title = "TDD with Java";
        Long creatorId = 1L;

        Course course = new Course(id, order, title, creatorId);

        Session session = createSession();

        course.addSession(session);

        Assertions.assertThat(course.getSessions()).hasSize(1);
        Assertions.assertThat(course.getSessions().get(0)).isEqualTo(session);
    }

    private Session createSession() {
        Long id = 1L;
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        SessionType type = SessionType.FREE;
        SessionStatus status = SessionStatus.PREPARING;
        Image image = new Image(1L, 1000L, ImageType.GIF, 300L, 200L);
        Long price = 400_000L;
        Long students = Long.MAX_VALUE;

        return Session.createFree(name, startDate, endDate, image);
    }
}
