package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static nextstep.courses.domain.SessionDateTest.sd1;
import static nextstep.courses.domain.SessionImageTest.si1;
import static nextstep.courses.domain.SessionStatus.END;
import static nextstep.courses.domain.SessionStatus.RECRUITING;
import static nextstep.payments.PaymentTest.p1;
import static nextstep.payments.PaymentTest.p2;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FreeSessionTest {

    public static final FreeSession fs1 = new FreeSession(1L, sd1, si1, END, new ArrayList<>());
    public static final FreeSession fs2 = new FreeSession(1L, sd1, si1, RECRUITING, new ArrayList<>());

    @Test
    @DisplayName("무료강의인데 금액을 지불할 경우, 예외를 발생시킨다.")
    void 지불한금액_존재() {
        assertThatThrownBy(() -> fs2.enroll(p1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("모집중이 아닌 경우, 예외를 발생시킨다.")
    void 모집중이아닌경우_강의신청불가() {
        assertThatThrownBy(() -> fs1.enroll(p2))
                .isInstanceOf(IllegalStateException.class);
    }

}
