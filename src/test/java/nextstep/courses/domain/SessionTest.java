package nextstep.courses.domain;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    @DisplayName("Course 가 진행중 상태로 생성된다.")
    void initCourseStatusTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.create(startAt, endAt, null, PaymentPolicy.FREE);

        Assertions.assertThat(session.getCourseStatus()).isEqualTo(CourseStatus.PENDING);
    }
}