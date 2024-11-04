package nextstep.courses.domain.session.sessionCoverImage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createHeight;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createRatio;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createWidth;

class RatioTest {

    @Test
    void create_올바른_비율인_경우() {
        Ratio ratio = createRatio(createWidth(300), createHeight(200));

        Assertions.assertThat(ratio).isNotNull();
    }

    @Test
    @DisplayName("가로와 세로 비율이 올바르지 않은 경우 IllegalArgumentException 가 발생한다.")
    void create_올바른_비율이_아닌_경우() {
        Assertions.assertThatThrownBy(() -> {
            Ratio ratio = createRatio(createWidth(400), createHeight(200));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
