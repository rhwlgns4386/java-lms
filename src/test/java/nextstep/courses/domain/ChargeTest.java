package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.courses.NonPositiveException;
import org.junit.jupiter.api.Test;

public class ChargeTest {

    @Test
    void 금액정상_생성() {
        assertThatCode(() -> new Charge(0)).doesNotThrowAnyException();
    }

    @Test
    void 금액이_같은지_확인한다() {
        assertThat(new Charge(100)).isEqualTo(new Charge(100));
    }

    @Test
    void 금액은_음수일_수없다() {
        assertThatThrownBy(() -> new Charge(-1)).isInstanceOf(NonPositiveException.class);
    }

    @Test
    void 금액은_null을_입력_할_수_없다() {
        assertThatNullPointerException().isThrownBy(() -> new Charge(null));
    }
}
