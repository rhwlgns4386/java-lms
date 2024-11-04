package nextstep.courses.domain.session.sessionCoverImage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionCoverImageTest {
    public static Width createWidth(int width) {
        return new Width(width);
    }

    public static Height createHeight(int height) {
        return new Height(height);
    }

    public static Ratio createRatio(Width width, Height height) {
        return new Ratio(width, height);
    }

    public static Size createSize(Long bytes) {
        return new Size(bytes);
    }

    @Test
    void create_정상케이스() {
        Ratio ratio = createRatio(createWidth(300), createHeight(200));
        Size size = createSize(1_048_575L);

        SessionCoverImage sessionCoverImage = new SessionCoverImage("jpg", ratio, size);

        Assertions.assertThat(sessionCoverImage).isNotNull();
    }

    @Test
    @DisplayName("허용되지 않은 확장자일 경우 IllegalArgumentException이 발생한다.")
    void create_허용되지_않은_확장자일_경우() {
        Ratio ratio = createRatio(createWidth(300), createHeight(200));
        Size size = createSize(1_048_575L);

        Assertions.assertThatThrownBy(() -> {
            SessionCoverImage sessionCoverImage = new SessionCoverImage("txt", ratio, size);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}