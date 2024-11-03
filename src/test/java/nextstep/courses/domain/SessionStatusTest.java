package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStatusTest {

    @DisplayName("문자열을 받아서 해당하는 SessionStatus를 반환한다")
    @Test
    void toSessionStatus() {
        String stringSessionStatus = "prepare";
        SessionStatus sessionStatus = SessionStatus.from(stringSessionStatus);
        assertThat(sessionStatus).isEqualTo(SessionStatus.PREPARE);
    }

    @DisplayName("수강신청이 안되는 상태인지 확인한다")
    @Test
    void checkSessionStatus() {
        boolean result = SessionStatus.cannotRegister(SessionStatus.CLOSE);
        assertThat(result).isTrue();
    }
}
