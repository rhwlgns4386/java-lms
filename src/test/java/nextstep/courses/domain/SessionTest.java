package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.SessionDateTest.sd1;
import static nextstep.courses.domain.SessionImageTest.si1;
import static nextstep.courses.domain.SessionStatus.PREPARING;

public class SessionTest {

//    @Test
//    @DisplayName("")

    @Test
    @DisplayName("유료강의의 정원초과일 경우, 예외를 발생시키낟.")
    void 유료강의_정원초과() {
        Assertions.assertThatThrownBy(() -> new PaidSession(sd1, si1, PREPARING, 101, 100))
                .isInstanceOf(IllegalStateException.class);
    }
}
