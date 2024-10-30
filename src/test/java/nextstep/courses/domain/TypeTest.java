package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeTest {

    @DisplayName("문자열을 받아서 Enum 값으로 변환한다")
    @Test
    void toType() {
        String stringType = "free";

        Type changedType = Type.from(stringType);

        assertThat(changedType).isEqualTo(Type.FREE);
    }

    @DisplayName("무료 강의인지 확인한다")
    @Test
    void isFree() {
        boolean result = Type.FREE.isFree();
        assertThat(result).isTrue();
    }

    @DisplayName("유료 강의인지 확인한다")
    @Test
    void isPaid() {
        boolean result = Type.PAID.isPaid();
        assertThat(result).isTrue();
    }
}
