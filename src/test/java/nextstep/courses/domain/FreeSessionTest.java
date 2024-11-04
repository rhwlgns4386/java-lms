package nextstep.courses.domain;

import nextstep.fixture.FreeSessionCreator;
import nextstep.fixture.PaymentCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.ProgressStatus.*;
import static nextstep.courses.domain.RecruitingStatus.NON_RECRUITING;
import static nextstep.courses.domain.RecruitingStatus.RECRUITING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FreeSessionTest {

    @Test
    @DisplayName("모집 상태가 모집중인 경우 정상적으로 강의신청이 가능하다.")
    void 모집중_강의신청가능() {
        FreeSessionCreator.status(RECRUITING, PREPARING).enroll(PaymentCreator.pay(0L));
        FreeSessionCreator.status(RECRUITING, PROGRESSING).enroll(PaymentCreator.pay(0L));
        FreeSessionCreator.status(RECRUITING, END).enroll(PaymentCreator.pay(0L));
    }

    @Test
    @DisplayName("모집 상태가 비모집중인 경우, 예외를 발생시킨다.")
    void 비모집중_강의신청불가() {
        Assertions.assertAll(
                () -> assertThatThrownBy(() -> FreeSessionCreator.status(NON_RECRUITING, PREPARING).enroll(PaymentCreator.pay(0L)))
                        .isInstanceOf(IllegalStateException.class),
                () -> assertThatThrownBy(() -> FreeSessionCreator.status(NON_RECRUITING, PROGRESSING).enroll(PaymentCreator.pay(0L)))
                        .isInstanceOf(IllegalStateException.class),
                () -> assertThatThrownBy(() -> FreeSessionCreator.status(NON_RECRUITING, END).enroll(PaymentCreator.pay(0L)))
                        .isInstanceOf(IllegalStateException.class)
        );
    }

    @Test
    @DisplayName("무료강의인데 금액을 지불할 경우, 예외를 발생시킨다.")
    void 지불한금액_존재() {
        assertThatThrownBy(() -> FreeSessionCreator.standard().enroll(PaymentCreator.pay(1000L)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("모집중이 아닌 경우, 예외를 발생시킨다.")
    void 모집중이아닌경우_강의신청불가() {
        assertThatThrownBy(() -> FreeSessionCreator.recruitingStatus(NON_RECRUITING).enroll(PaymentCreator.pay(0L)))
                .isInstanceOf(IllegalStateException.class);
    }

}
