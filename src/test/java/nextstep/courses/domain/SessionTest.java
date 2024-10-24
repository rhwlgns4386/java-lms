package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.SessionDateTest.sd1;
import static nextstep.courses.domain.SessionImageTest.si1;
import static nextstep.courses.domain.SessionStatus.PREPARING;
import static nextstep.courses.domain.SessionStatus.RECRUITING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    @Test
    @DisplayName("강의 신청 시 정원 초과일 경우, 예외를 발생시킨다.")
    void 유료강의_강의신청_정원초과() {
        PaidSession paidSession = new PaidSession(sd1, si1, RECRUITING, 100, 100);
        assertThatThrownBy(() -> paidSession.enroll(1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("유료강의의 정원초과일 경우, 예외를 발생시킨다.")
    void 유료강의_정원초과() {
        assertThatThrownBy(() -> new PaidSession(sd1, si1, PREPARING, 101, 100))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
