package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    @DisplayName("Course 가 진행중 상태로 생성된다.")
    void initCourseStatusTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.create(startAt, endAt, null, PaymentPolicy.FREE);

        assertThat(session.getCourseStatus()).isEqualTo(CourseStatus.PENDING);
    }

    @Test
    @DisplayName("open 메서드가 강의 상태를 모집중으로 변경한다.")
    void openTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.create(startAt, endAt, null, PaymentPolicy.FREE);

        session.open();

        assertThat(session.getCourseStatus()).isEqualTo(CourseStatus.OPEN);
    }
}