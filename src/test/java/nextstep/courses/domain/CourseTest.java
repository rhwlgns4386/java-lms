package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {

    @DisplayName("과정에 강의를 추가한다")
    @Test
    void addSession() {
        Course course = new Course();
        Session session = createSession();

        course.addSession(session);
        course.addSession(session);

        assertThat(course.getSessions()).hasSize(2);
    }

    private Session createSession() {
        SessionPeriod dateRange = new SessionPeriod(
                LocalDateTime.of(2024, 10, 1, 0, 0),
                LocalDateTime.of(2024, 10, 5, 23, 59)
        );

        Pricing pricing = new Pricing(PricingType.PAID, 10000);
        SessionImage image = new SessionImage(1L, "/image.png", 100, new Size(300, 200), 1L, LocalDateTime.now(), LocalDateTime.now());
        return new Session(1L, dateRange, image, pricing, null, 50, 1L, LocalDateTime.now());
    }
}
