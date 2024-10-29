package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class CourseTest {
    private Long courseId = NsUserTest.JAVAJIGI.getId();
    private Long order = 1L;
    private String title = "TDD with Java";
    private Long creatorId = NsUserTest.JAVAJIGI.getId();

    private FreeSession freeSession;
    private PaidSession paidSession;

    @BeforeEach
    void init() {
        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImageProperty(300L, 200L));

        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        SessionDate sessionDate = new SessionDate(start, end);
        SessionId sessionId = SessionId.of(1L, "TDD");
        this.freeSession = new FreeSession(sessionId, sessionDate, image);

        sessionId = SessionId.of(2L, "TDD");
        SessionCapacity sessionCapacity = new SessionCapacity(1);
        Money fee = new Money(200_000L);

        this.paidSession = new PaidSession(image, sessionDate, sessionId, sessionCapacity, fee);
    }

    @Test
    @DisplayName("Course class 생성")
    void createCourseTest() {
        Course course = new Course(courseId, order, title, creatorId);

        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course.getId()).isEqualTo(courseId);
        Assertions.assertThat(course.getOrder()).isEqualTo(order);
    }

    @Test
    @DisplayName("과정에 강의 추가")
    void addFreeSessionToCourseTest() {
        Course course = new Course(courseId, order, title, creatorId);

        course.addSessions(freeSession, paidSession);

        Assertions.assertThat(course.getSessions()).hasSize(2);
        Assertions.assertThat(course.getSessions()).hasSameElementsAs(List.of(freeSession, paidSession));
    }

}
