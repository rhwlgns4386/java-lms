package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaidSessionTest {

    private CoverImage coverImage;
    private SessionPeriod sessionPeriod;

    @BeforeEach
    void setUp() {
        coverImage = new CoverImage(new CoverImageFile(30), CoverImageType.GIF, new CoverImageSize(300, 200));
        sessionPeriod = new SessionPeriod(LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 10).plusDays(30));
    }

    @DisplayName("수강신청을 성공한다.")
    @Test
    void register() {
        SessionCapacity capacity = new SessionCapacity(5, 10);
        Money courseFee = new Money(10000);
        PaidSession course = new PaidSession(SessionStatus.OPEN, sessionPeriod, capacity, courseFee, coverImage);
        Payment payment = new Payment("PG1", 1L, 1L, 10000L);

        course.register(payment);

        assertThat(course)
                .extracting("capacity")
                .isEqualTo(new SessionCapacity(6, 10));
    }

    @DisplayName("OPEN 상태가 아닌 강의는 수강신청을 할 수 없다.")
    @Test
    void register_NotOpenStatus() {
        SessionCapacity capacity = new SessionCapacity(5, 10);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.CLOSED, sessionPeriod, capacity, courseFee, coverImage);
        Payment payment = new Payment("PG1", 1L, 1L, 10000L);

        assertThatThrownBy(() -> paidCourse.register(payment))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최대 수강 인원을 초과한 강의에 등록하는 경우 예외로 처리한다.")
    @Test
    void register_FullCapacity() {
        SessionCapacity capacity = new SessionCapacity(10, 10);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.OPEN, sessionPeriod, capacity, courseFee, coverImage);
        Payment payment = new Payment("PG1", 1L, 1L, 10000L);

        assertThatThrownBy(() -> paidCourse.register(payment))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("결제금액과 수강료가 일치하지 않으면 예외로 처리한다.")
    @Test
    void register_InvalidPaymentAmount() {
        SessionCapacity capacity = new SessionCapacity(9, 10);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.OPEN, sessionPeriod, capacity, courseFee, coverImage);
        Payment payment = new Payment("PG1", 1L, 1L, 9000L);

        assertThatThrownBy(() -> paidCourse.register(payment))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("결제 정보가 없을 경우 예외로 처리한다.")
    @Test
    void register_NullPayment() {
        SessionCapacity capacity = new SessionCapacity(9, 10);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.OPEN, sessionPeriod, capacity, courseFee, coverImage);

        assertThatThrownBy(() -> paidCourse.register(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
