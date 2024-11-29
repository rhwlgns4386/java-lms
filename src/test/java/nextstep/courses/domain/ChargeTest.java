package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.NonPositiveException;
import org.junit.jupiter.api.Test;

public class ChargeTest {

    @Test
    void 픽셀정상_생성() {
        assertThatCode(() -> new Charge(0)).doesNotThrowAnyException();
    }

    @Test
    void 픽셀은_음수일_수없다() {
        assertThatThrownBy(() -> new Charge(-1)).isInstanceOf(NonPositiveException.class);
    }

    @Test
    void 금액은_null을_입력_할_수_없다() {
        assertThatNullPointerException().isThrownBy(() -> new Charge(null));
    }
}
