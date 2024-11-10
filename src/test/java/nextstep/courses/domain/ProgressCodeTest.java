package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProgressCodeTest {

    @Test
    @DisplayName("강의가 종료가 아닌 경우 수강신청 가능")
    void validateOrderSessionProgressCode() {
        ProgressCode.PROGRESS.validateOrderSessionProgressCode();;
        ProgressCode.READY.validateOrderSessionProgressCode();;
    }

    @Test
    @DisplayName("강의가 종료인 경우 수강신청 불가 오류")
    void validateOrderSessionProgressCode_IllegalArgumentException() {
        assertThatThrownBy(() -> {
            ProgressCode.END.validateOrderSessionProgressCode();
        });
    }
}
