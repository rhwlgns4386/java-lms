package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class SessionTest {

    @DisplayName("강의 진행 상태가 종료이면 수강 신청 할 수 없다")
    @Test
    void validateSessionStatus() {
        Session session = createSession(new Status(ProgressStatus.FINISHED, RecruitmentStatus.NOT_RECRUITING), PricingType.PAID, 10000, 50);

        assertThatIllegalStateException()
                .isThrownBy(() -> session.enrollStudent(NsUserTest.JAVAJIGI, 10000))
                .withMessage("강의 상태가 모집 중이 아니면 수강 신청 할 수 없습니다.");
    }
    
    @DisplayName("강의 진행 상태가 진행 중이어도 모집 상태가 모집 중이면 모집 가능하다")
    @Test
    void validateStatus() {
        Session session = createSession(new Status(ProgressStatus.IN_PROGRESS, RecruitmentStatus.RECRUITING), PricingType.PAID, 10000, 50);


        session.enrollStudent(NsUserTest.JAVAJIGI, 10000);

        SessionStudents students = session.getStudents();
        assertThat(students.size()).isEqualTo(1);
    }

    @DisplayName("유료강의는 강의 가격과 지불한 돈이 일치하지 않으면 수강할 수 없다")
    @Test
    void lessPay() {
        Session session = createSession(new Status(ProgressStatus.IN_PROGRESS, RecruitmentStatus.RECRUITING), PricingType.PAID, 10000, 50);

        assertThatIllegalStateException()
                .isThrownBy(() -> session.enrollStudent(NsUserTest.JAVAJIGI, 9999))
                .withMessage("강의 가격과 지불한 돈이 일치하지 않습니다.");

    }

    private Session createSession(Status status, PricingType type, int price, int enrollmentLimit) {
        SessionPeriod dateRange = new SessionPeriod(
                LocalDateTime.of(2024, 10, 1, 0, 0),
                LocalDateTime.of(2024, 10, 5, 23, 59)
        );
        Pricing pricing = new Pricing(type, price);
        return new Session(1L, dateRange, status, null, pricing, new SessionStudents(), enrollmentLimit, 1L, LocalDateTime.now());
    }
}
