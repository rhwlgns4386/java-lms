package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExtensionTest {

    @Test
    void 생성() {
        boolean result = Extension.verify("png");
        assertThat(result).isTrue();
    }
}
