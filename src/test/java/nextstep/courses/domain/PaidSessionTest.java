package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaidSessionTest {
    @DisplayName("유료 강의는 강의 최대 수강 인원을 초과할 수 없다.")
    @Test
    void 최대_수강인원_초과_에러() {
        Session session = new PaidSession("세션", LocalDate.of(2024, 10, 28),
                LocalDate.of(2024, 11, 28), SessionStatus.OPEN, 0, 10000L, new SessionImage(600, 300, 200, "jpg"));
        assertThatThrownBy(() -> {
            session.register(JAVAJIGI, new Payment(session.getId(), JAVAJIGI.getId(), 10000L));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.")
    @Test
    void 강의_금액_에러() {
        Session session = new PaidSession("세션", LocalDate.of(2024, 10, 28),
                LocalDate.of(2024, 11, 28), SessionStatus.OPEN, 0, 10000L, new SessionImage(600, 300, 200, "jpg"));
        assertThatThrownBy(() -> {
            session.register(JAVAJIGI, new Payment(session.getId(), JAVAJIGI.getId(), 20000L));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
