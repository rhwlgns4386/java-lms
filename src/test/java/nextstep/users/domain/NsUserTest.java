package nextstep.users.domain;

import nextstep.users.NsUserTestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NsUserTest {

    @DisplayName("두 NsUser 객체가 동일할 때 true를 반환한다.")
    @Test
    void return_true_when_two_arguments_are_same() {
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser loginMoon = NsUserTestFixture.builder().createdAt(createdAt).build();
        NsUser activeMoon = NsUserTestFixture.builder().createdAt(createdAt).build();

        assertTrue(loginMoon.isSameUser(activeMoon));
    }
}
