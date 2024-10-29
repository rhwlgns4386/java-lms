package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionImageTest {

    @Test
    void create() {
        SessionImage expected =  new SessionImage(1000000, "jpg", 300, 200);

        assertThat(expected).isEqualTo(new SessionImage(1000000, "jpg", 300, 200));
    }


    @Test
    void 생성자_예외_테스트() {
        assertThatThrownBy(() -> {
            new SessionImage(10000000, "jpg", 300, 200);
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            new SessionImage(1000, "3ds", 300, 200);
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            new SessionImage(1000, "jpg", 150, 100);
        }).isInstanceOf(IllegalArgumentException.class);
    }



}
