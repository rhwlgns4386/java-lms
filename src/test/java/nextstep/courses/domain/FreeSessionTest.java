package nextstep.courses.domain;

import nextstep.fixture.FreeSessionCreator;
import nextstep.fixture.PaymentCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.SessionStatus.END;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FreeSessionTest {

    @Test
    @DisplayName("무료강의인데 금액을 지불할 경우, 예외를 발생시킨다.")
    void 지불한금액_존재() {
        assertThatThrownBy(() -> FreeSessionCreator.standard().enroll(PaymentCreator.pay(1000L)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("모집중이 아닌 경우, 예외를 발생시킨다.")
    void 모집중이아닌경우_강의신청불가() {
        assertThatThrownBy(() -> FreeSessionCreator.status(END).enroll(PaymentCreator.pay(0L)))
                .isInstanceOf(IllegalStateException.class);
    }

}
