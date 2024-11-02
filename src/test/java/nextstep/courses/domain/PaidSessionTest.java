package nextstep.courses.domain;

import nextstep.fixture.PaidSessionCreator;
import nextstep.fixture.PaymentCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.courses.domain.ProgressStatus.*;
import static nextstep.courses.domain.RecruitingStatus.NON_RECRUITING;
import static nextstep.courses.domain.RecruitingStatus.RECRUITING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaidSessionTest {
    @Test
    @DisplayName("모집 상태가 모집중인 경우 정상적으로 강의신청이 가능하다.")
    void 모집중_강의신청가능() {
        PaidSessionCreator.status(RECRUITING, PREPARING).enroll(PaymentCreator.pay(25000L));
        PaidSessionCreator.status(RECRUITING, PROGRESSING).enroll(PaymentCreator.pay(25000L));
        PaidSessionCreator.status(RECRUITING, END).enroll(PaymentCreator.pay(25000L));
    }

    @Test
    @DisplayName("모집 상태가 비모집중인 경우, 예외를 발생시킨다.")
    void 비모집중_강의신청불가() {
        Assertions.assertAll(
                () -> assertThatThrownBy(() -> PaidSessionCreator.status(NON_RECRUITING, PREPARING).enroll(PaymentCreator.pay(0L)))
                        .isInstanceOf(IllegalStateException.class),
                () -> assertThatThrownBy(() -> PaidSessionCreator.status(NON_RECRUITING, PROGRESSING).enroll(PaymentCreator.pay(0L)))
                        .isInstanceOf(IllegalStateException.class),
                () -> assertThatThrownBy(() -> PaidSessionCreator.status(NON_RECRUITING, END).enroll(PaymentCreator.pay(0L)))
                        .isInstanceOf(IllegalStateException.class)
        );
    }

    @Test
    @DisplayName("결제한 금액과 수강료가 일치하지 않을 때, 예외를 발생시킨다.")
    void 결제금액_불일치() {
        assertThatThrownBy(() -> PaidSessionCreator.fee(2999).enroll(PaymentCreator.pay(3000L)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("모집중이 아닌 경우, 예외를 발생시킨다.")
    void 모집중이아닌경우_강의신청불가() {
        assertThatThrownBy(() -> PaidSessionCreator.recruitingStatus(NON_RECRUITING).enroll(PaymentCreator.pay(3000L)))
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
