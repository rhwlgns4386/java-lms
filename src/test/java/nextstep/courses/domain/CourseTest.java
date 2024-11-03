package nextstep.courses.domain;

import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPriceType;
import nextstep.courses.domain.session.SessionStatus;
import nextstep.courses.domain.session.Sessions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static nextstep.courses.domain.session.Session.*;
import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {

    @Test
    void 코스_세션_추가_테스트() {
        Course course = new Course();
        SessionCoverImage sessionCoverImage = new SessionCoverImage(1L, 1L,150, "leo.png", 300, 200);

        Session session = new SessionBuilder(1L)
                .price(1000L)
                .sessionPriceType(SessionPriceType.FREE)
                .sessionStatus(SessionStatus.COMPLETED)
                .sessionCoverImage(sessionCoverImage)
                .startDateTime(LocalDateTime.of(2024, 10, 30, 10, 30))
                .endDateTime(LocalDateTime.of(2024, 11, 20, 10, 30))
                .availableEnrollCount(30)
                .build();
        course.addCourseSession(session);

        assertThat(course.getSessions())
                .isEqualTo(new Sessions(List.of(createSessionOf(1L, 1000L, SessionPriceType.FREE, SessionStatus.COMPLETED, sessionCoverImage, LocalDateTime.of(2024, 10, 30, 10, 30), LocalDateTime.of(2024, 11, 20, 10, 30), 30))));
    }

}