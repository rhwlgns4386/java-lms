package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaidSessionTest {

    @DisplayName("수강신청을 성공한다.")
    @Test
    void register() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        SessionPeriod period = new SessionPeriod(startDate, endDate);

        SessionCapacity capacity = new SessionCapacity(5, 10);
        Money courseFee = new Money(10000);
        Money paidAmount = new Money(10000);

        PaidSession course = new PaidSession(SessionStatus.OPEN, period, capacity, courseFee);

        course.register(paidAmount);

        assertThat(course)
                .extracting("capacity")
                .isEqualTo(new SessionCapacity(6, 10));
    }

    @DisplayName("OPEN 상태가 아닌 강의는 수강신청을 할 수 없다.")
    @Test
    void register_NotOpenStatus() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        SessionPeriod period = new SessionPeriod(startDate, endDate);

        SessionCapacity capacity = new SessionCapacity(5, 10);
        Money courseFee = new Money(10000);
        Money paidAmount = new Money(10000);

        PaidSession paidCourse = new PaidSession(SessionStatus.CLOSED, period, capacity, courseFee);

        assertThatThrownBy(() -> paidCourse.register(paidAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최대 수강 인원을 초과한 강의에 등록하는 경우 예외로 처리한다.")
    @Test
    void register_FullCapacity() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        SessionPeriod period = new SessionPeriod(startDate, endDate);

        SessionCapacity capacity = new SessionCapacity(10, 10);
        Money courseFee = new Money(10000);
        Money paidAmount = new Money(10000);

        PaidSession paidCourse = new PaidSession(SessionStatus.OPEN, period, capacity, courseFee);

        assertThatThrownBy(() -> paidCourse.register(paidAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("결제금액과 수강료가 일치하지 않으면 예외로 처리한다.")
    @Test
    void register_InvalidPayment() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        SessionPeriod period = new SessionPeriod(startDate, endDate);

        SessionCapacity capacity = new SessionCapacity(10, 10);
        Money courseFee = new Money(10000);
        Money paidAmount = new Money(9000);

        PaidSession paidCourse = new PaidSession(SessionStatus.OPEN, period, capacity, courseFee);

        assertThatThrownBy(() -> paidCourse.register(paidAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
