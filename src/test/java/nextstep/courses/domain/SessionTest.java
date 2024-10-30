package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    @DisplayName("Session 가 진행중 상태로 생성된다.")
    void initCourseStatusTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.createFreeSession(startAt, endAt, null);

        assertThat(session.getCourseStatus()).isEqualTo(CourseStatus.PENDING);
    }

    @Test
    @DisplayName("open 메서드가 강의 상태를 모집중으로 변경한다.")
    void openTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.createFreeSession(startAt, endAt, null);

        session.open();

        assertThat(session.getCourseStatus()).isEqualTo(CourseStatus.OPEN);
    }

    @Test
    @DisplayName("유료강의 수강신청 시 인원이 초과된 경우 예외가 발생한다.")
    void throwExceptionWhenOverCapacity() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.createPaidSession(startAt, endAt, null, 1);

        session.register();

        assertThatThrownBy(() -> session.register())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("정원을 초과했습니다.");
    }

    @Test
    @DisplayName("수강신청 시 수강인원이 증가한다.")
    void resTest() {
        LocalDate startAt = LocalDate.of(2024, 1, 1);
        LocalDate endAt = LocalDate.of(2024, 12, 1);
        Session session = Session.createFreeSession(startAt, endAt, null);

        session.register();

        assertThat(session.getEnrollStudentCount()).isOne();
    }

}