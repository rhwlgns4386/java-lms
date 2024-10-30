package nextstep.sessions.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.*;

public class SessionPeriodTest {
    @Test
    @DisplayName("시작일은 종료일보다 커거나 같을수 없다")
    void validation() {
        assertThatThrownBy(() -> new SessionPeriod("20241031", "20241030")).isInstanceOf(IllegalArgumentException.class);

    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("시작일 빈값 검증")
    void validation_emptyValue_startDt(String test) {
        assertThatThrownBy(() -> new SessionPeriod(test, "20241030")).isInstanceOf(IllegalArgumentException.class);

    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("종료일 빈값 검증")
    void validation_emptyValue_endDt(String testDt) {
        assertThatThrownBy(() -> new SessionPeriod("20250101",  testDt)).isInstanceOf(IllegalArgumentException.class);

    }

}
