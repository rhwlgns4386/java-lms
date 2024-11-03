package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImagesTest {

    @DisplayName("SessionImage 컬렉션으로 SessionImages를 생성한다")
    @Test
    void create() {
        List<Image> sessionImageList = List.of(
            SessionTestFixture.createSessionImage(),
            SessionTestFixture.createSessionImage(),
            SessionTestFixture.createSessionImage(),
            SessionTestFixture.createSessionImage()
        );

        Images sessionImages = new Images(1L, sessionImageList);
        assertThat(sessionImages).isEqualTo(new Images(1L, sessionImageList));
    }

    @DisplayName("이미지를 추가한다")
    @Test
    void addImage() {
        List<Image> sessionImageList = List.of(
            SessionTestFixture.createSessionImage(),
            SessionTestFixture.createSessionImage(),
            SessionTestFixture.createSessionImage(),
            SessionTestFixture.createSessionImage()
        );

        Images sessionImages = new Images(1L, sessionImageList);

        sessionImages.add(SessionTestFixture.createSessionImage());

        assertThat(sessionImages.size()).isEqualTo(5);
    }
}
