package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static nextstep.courses.domain.SessionDateTest.sd1;
import static nextstep.courses.domain.SessionImageTest.si1;
import static nextstep.courses.domain.SessionStatus.END;
import static nextstep.courses.domain.SessionStatus.RECRUITING;
import static nextstep.payments.PaymentTest.p1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaidSessionTest {

    public static final PaidSession ps1 = new PaidSession(1L, sd1, si1, END, new ArrayList<>(), 80, 25000);
    public static final PaidSession ps2 = new PaidSession(1L, sd1, si1, RECRUITING, List.of(1L), 1, 25000);
    public static final PaidSession ps3 = new PaidSession(1L, sd1, si1, RECRUITING, List.of(1L), 1, 25000);
    public static final PaidSession ps4 = new PaidSession(1L, sd1, si1, RECRUITING, List.of(1L), 80, 25000);

    @Test
    @DisplayName("결제한 금액과 수강료가 일치하지 않을 때, 예외를 발생시킨다.")
    void 결제금액_불일치() {
        assertThatThrownBy(() -> ps4.enroll(p1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("모집중이 아닌 경우, 예외를 발생시킨다.")
    void 모집중이아닌경우_강의신청불가() {
        assertThatThrownBy(() -> ps1.enroll(p1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("강의 신청 시 정원 초과일 경우, 예외를 발생시킨다.")
    void 유료강의_강의신청_정원초과() {
        assertThatThrownBy(() -> ps2.enroll(p1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("유료강의의 처음 상태가 정원초과일 경우, 예외를 발생시킨다.")
    void 유료강의_정원초과() {
        assertThatThrownBy(() -> new PaidSession(1L, sd1, si1, END, List.of(1L), 0, 25000))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
