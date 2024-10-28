package nextstep.users.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NsUserTest {

    @DisplayName("두 NsUser 객체가 동일할 때 true를 반환한다.")
    @Test
    void return_true_when_two_arguments_are_same() {
        NsUser moon = new NsUser(1L, "moon", "1234", "moonyoonji", "moon@a.com");
        NsUser zi = new NsUser(1L, "zi", "5678", "moonzi", "moonz@a.com");

        assertTrue(moon.isSameUser(zi));
    }
}
