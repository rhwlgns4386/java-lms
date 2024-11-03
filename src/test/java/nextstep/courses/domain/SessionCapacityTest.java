package nextstep.courses.domain;

import nextstep.courses.domain.session.SessionCapacity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionCapacityTest {
    @Test
    @DisplayName("SessionCapacity 생성")
    void createSessionCapacityTest() {
        int capacity = 10;

        SessionCapacity sessionCapacity = new SessionCapacity(capacity);

        Assertions.assertThat(sessionCapacity).isNotNull();
    }

    @Test
    @DisplayName("capacity 0이하 체크")
    void checkCapacityTest() {
        int capacity = 0;

        Assertions.assertThatThrownBy(() -> new SessionCapacity(capacity))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
