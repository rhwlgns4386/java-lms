package nextstep.courses.domain;

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

    @Test
    @DisplayName("수강행 추가")
    void addCurrentCountTest(){
        SessionCapacity sessionCapacity = new SessionCapacity(10);
        sessionCapacity.increase();

        SessionCapacity expectedSessionCapacity = new SessionCapacity(10, 1);
        Assertions.assertThat(sessionCapacity).isEqualTo(expectedSessionCapacity);
    }

    @Test
    @DisplayName("수강생 초과 체크")
    void checkOverCurrentCountTest(){
        SessionCapacity sessionCapacity = new SessionCapacity(1);

        sessionCapacity.increase();

        Assertions.assertThatThrownBy(sessionCapacity::increase)
            .isInstanceOf(IllegalArgumentException.class);
    }
}
