package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @Test
    void 숫자가_큰지_검증한다() {
        assertAll(() -> {
            assertThat(new Positive(300).isLessThanOrEqualTo(new Positive(300))).isTrue();
            assertThat(new Positive(300).isLessThanOrEqualTo(new Positive(301))).isTrue();
        });
    }
}
