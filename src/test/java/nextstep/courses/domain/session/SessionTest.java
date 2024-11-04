package nextstep.courses.domain.session;

import nextstep.courses.domain.strategy.FreePaymentStrategy;
import nextstep.courses.domain.strategy.PaidPaymentStrategy;
import nextstep.courses.domain.strategy.PaymentStrategy;
import nextstep.courses.exception.CannotEnrollmentException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {
    @Test
    void 유료강의_수강신청_성공() {
        PaidPaymentStrategy paidSessionStrategy = new PaidPaymentStrategy(1000);
        Session session = new Session(1000000, "jpg", 300, 200, SessionState.RECRUITING, paidSessionStrategy, 10, LocalDate.now(), LocalDate.now().plusDays(3));
        Payment payment = new Payment("1L", 1L, 1L, 1000l);

        session.applyForCourse(NsUserTest.JAVAJIGI, payment, LocalDate.now());
    }

    @Test
    void 유료강의_수강신청_실패() {
        PaymentStrategy paymentStrategy = new PaidPaymentStrategy(1000);
        Session session = new Session(1000000, "jpg", 300, 200, SessionState.RECRUITING, paymentStrategy, 10, LocalDate.now(), LocalDate.now().plusDays(3));
        Payment payment = new Payment("1L", 1L, 1L, 10000l);

        assertThatThrownBy(() -> {
            session.applyForCourse(NsUserTest.JAVAJIGI, payment, LocalDate.now());
        }).isInstanceOf(CannotEnrollmentException.class);
    }

    @Test
    void 무료강의_수강신청_성공() {
        PaymentStrategy paymentStrategy = new FreePaymentStrategy();
        Session session = new Session(1000000, "jpg", 300, 200, SessionState.RECRUITING, paymentStrategy, 10, LocalDate.now(), LocalDate.now().plusDays(3));
        Payment payment = new Payment("1L", 1L, 1L, 0L);

        session.applyForCourse(NsUserTest.JAVAJIGI, payment, LocalDate.now());
    }
}
