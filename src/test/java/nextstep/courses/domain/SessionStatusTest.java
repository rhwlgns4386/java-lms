package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStatusTest {

    @DisplayName("모집중인지 확인한다")
    @Test
    void checkSameStatus() {
        boolean result = SessionStatus.isNotRegister(SessionStatus.REGISTER);
        boolean result2 = SessionStatus.isNotRegister(SessionStatus.PREPARE);

        assertThat(result).isFalse();
        assertThat(result2).isTrue();
    }

    @DisplayName("문자열을 받아서 해당하는 SessionStatus를 반환한다")
    @Test
    void toSessionStatus() {
        String stringSessionStatus = "register";
        SessionStatus sessionStatus = SessionStatus.from(stringSessionStatus);
        assertThat(sessionStatus).isEqualTo(SessionStatus.REGISTER);
    }
}
