package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.SessionImageSizeTest.is1;

public class SessionImageTest {

    public static final SessionImage si1 = new SessionImage(512, SessionImageType.getExtension("png"), is1);

    @Test
    @DisplayName("이미지의 크기가 1MB 이상일 경우, 예외를 발생시킨다.")
    void 이미지_크기_체크() {
        Assertions.assertThatThrownBy(() -> new SessionImage(1025, SessionImageType.getExtension("gif"), new SessionImageSize(400, 300)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
