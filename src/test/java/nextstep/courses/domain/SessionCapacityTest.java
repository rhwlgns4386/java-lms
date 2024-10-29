package nextstep.courses.domain;

import nextstep.courses.exception.CannotIncreaseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionCapacityTest {

    @Test
    void 증가_성공_테스트() {
        SessionCapacity sessionCapacity = new SessionCapacity(1);

        sessionCapacity.increase();

        assertThat(sessionCapacity).isEqualTo(new SessionCapacity(1, 1));
    }

    @Test
    void 증가_실패_테스트() {
        SessionCapacity sessionCapacity = new SessionCapacity(0);

        assertThatThrownBy(() -> {
            sessionCapacity.increase();
        }).isInstanceOf(CannotIncreaseException.class);
    }


}
