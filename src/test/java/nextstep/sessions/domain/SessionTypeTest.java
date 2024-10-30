package nextstep.sessions.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionTypeTest {
    public static SessionType PAID_SESSION = new SessionType(Long.valueOf(200000), 100);
    public static SessionType FREE_SESSION = new SessionType();
    @Test
    @DisplayName("유료강의_최대인원검증")
    void check_MaxNumberOfUser() {

        Assertions.assertThatThrownBy(() -> PAID_SESSION.checkMaxNumber(101)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("유료강의_결제금액")
    void check_payAmount() {
        Assertions.assertThatThrownBy(() -> PAID_SESSION.validatePaymentAmount(Long.valueOf(220000))).isInstanceOf(RuntimeException.class);
    }


    @Test
    @DisplayName("무료강의_결제금액")
    void free_session_checkAmount() {
        Assertions.assertThatCode(() -> FREE_SESSION.validatePaymentAmount(Long.valueOf(0))).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("무료강의_최대인원검증통과")
    void free_session_checkNumbersOfPeople() {
        Assertions.assertThatCode(() -> FREE_SESSION.checkMaxNumber(1000)).doesNotThrowAnyException();
    }

}
