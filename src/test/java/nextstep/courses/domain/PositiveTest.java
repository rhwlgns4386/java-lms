package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.NonPositiveException;
import org.junit.jupiter.api.Test;

public class PositiveTest {

    @Test
    void 정상_생성() {
        assertThatCode(() -> new Positive(0)).doesNotThrowAnyException();
    }

    @Test
    void 음수일_수없다() {
        assertThatThrownBy(() -> new Positive(-1)).isInstanceOf(NonPositiveException.class);
    }
}
