package nextstep.courses.domain;

import nextstep.fixture.PaidSessionCreator;
import nextstep.fixture.PaymentCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.courses.domain.SessionStatus.END;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaidSessionTest {
    @Test
    @DisplayName("결제한 금액과 수강료가 일치하지 않을 때, 예외를 발생시킨다.")
    void 결제금액_불일치() {
        assertThatThrownBy(() -> PaidSessionCreator.fee(2999).enroll(PaymentCreator.pay(3000L)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("모집중이 아닌 경우, 예외를 발생시킨다.")
    void 모집중이아닌경우_강의신청불가() {
        assertThatThrownBy(() -> PaidSessionCreator.status(END).enroll(PaymentCreator.pay(3000L)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("강의 신청 시 정원 초과일 경우, 예외를 발생시킨다.")
    void 유료강의_강의신청_정원초과() {
        assertThatThrownBy(() -> PaidSessionCreator.capacity(List.of(1L), 1).enroll(PaymentCreator.pay(3000L)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("유료강의의 처음 상태가 정원초과일 경우, 예외를 발생시킨다.")
    void 유료강의_정원초과() {
        assertThatThrownBy(() -> PaidSessionCreator.capacity(List.of(1L, 2L), 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
