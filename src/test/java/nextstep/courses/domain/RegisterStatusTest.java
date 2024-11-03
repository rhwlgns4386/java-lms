package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterStatusTest {

    @DisplayName("모집중인지 확인한다")
    @Test
    void checkRegister() {
        boolean result = RegisterStatus.isNotRegister(RegisterStatus.REGISTER);
        boolean result2 = RegisterStatus.isNotRegister(RegisterStatus.NOTREGISTER);

        assertThat(result).isFalse();
        assertThat(result2).isTrue();
    }
}
