package nextstep.courses.domain;

import nextstep.fixture.SessionImageCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionImageTest {

    @Test
    @DisplayName("이미지의 크기가 1MB 이상일 경우, 예외를 발생시킨다.")
    void 이미지_크기_체크() {
        Assertions.assertThatThrownBy(() -> SessionImageCreator.volume(1025))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
