package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SessionImageTest {

    @Test
    void 생성() {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaInfo imageMetaInfo = new ImageMetaInfo(1, Extension.JPG);
        SessionImage sessionImage = new SessionImage(imageSize, imageMetaInfo);
        assertThat(sessionImage).isEqualTo(new SessionImage(imageSize, imageMetaInfo));
    }
}
