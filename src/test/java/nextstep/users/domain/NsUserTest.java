package nextstep.users.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NsUserTest {

    @DisplayName("두 NsUser 객체가 동일할 때 true를 반환한다.")
    @Test
    void return_true_when_two_arguments_are_same() {
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser loginMoon = generateNsUserTestFixture(0L, "moon", createdAt);
        NsUser activeMoon = generateNsUserTestFixture(0L, "moon", createdAt);

        assertTrue(loginMoon.isSameUser(activeMoon));
    }

    public static NsUser generateNsUserTestFixture(Long id, String userId, LocalDateTime createdAt) {
        return new NsUser(id, userId, "1234", userId, userId, createdAt, null);
    }

}
