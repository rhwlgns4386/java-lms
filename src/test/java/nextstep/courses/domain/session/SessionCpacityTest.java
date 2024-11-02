package nextstep.courses.domain.session;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionCpacityTest {

    @DisplayName("최대 수강인원이 0명이면 예외로 처리한다.")
    @Test
    void invalidMaxCapacity() {
        assertThatThrownBy(() -> new Capacity(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("현재 수강인원이 최대 수강인원을 초과하면 예외로 처리한다.")
    @Test
    void currentCapacityOverMaxCapacity() {
        Capacity capacity = new Capacity(1);
        Capacity updatedCapacity = capacity.register(NsUserTest.GREEN);

        assertThatThrownBy(() -> updatedCapacity.register(NsUserTest.JAVAJIGI))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
