package nextstep.courses.domain.session;

import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.session.SessionPrice.createSessionPriceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionPriceTest {

    @Test
    void 무료_가격_저장_테스트() {
        SessionPrice sessionPrice = createSessionPriceOf(SessionPriceType.FREE, 0L);

        assertThat(sessionPrice.getPrice()).isEqualTo(createSessionPriceOf(SessionPriceType.FREE, 0L).getPrice());
    }

    @Test
    void 유료_가격_저장_테스트() {
        SessionPrice sessionPrice = createSessionPriceOf(SessionPriceType.PAID, 1000L);

        assertThat(sessionPrice.getPrice()).isEqualTo(createSessionPriceOf(SessionPriceType.PAID, 1000L).getPrice());
    }

    @Test
    void 가격_빈_값_오류_테스트() {
        assertThatThrownBy(() -> createSessionPriceOf(SessionPriceType.PAID, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("가격은 꼭 입력해야합니다.");
    }

    @Test
    void 가격_0_이하_오류_테스트() {
        assertThatThrownBy(() -> createSessionPriceOf(SessionPriceType.PAID, -1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("가격은 0 이상이어야 합니다.");

    }

}
