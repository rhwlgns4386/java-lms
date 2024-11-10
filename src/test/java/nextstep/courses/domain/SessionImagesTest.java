package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionImagesTest {

    public final static SessionImages SESSION_IMAGE = new SessionImages(List.of(new SessionImage(200, "png", 600, 400, "imageFileName"),
            new SessionImage(300, "jpg", 300, 200, "imageFileName2")));

    @Test
    @DisplayName("강의는 이미지를 한개 이상 가지고 있어야함 ")
    void validationSessionImage() {

        assertThatThrownBy(() -> {
            SessionImages image = new SessionImages(Collections.EMPTY_LIST);
        }).isInstanceOf(IllegalArgumentException.class);

    }
}
