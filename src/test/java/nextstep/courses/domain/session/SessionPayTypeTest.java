package nextstep.courses.domain.session;

import nextstep.courses.SessionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionPayTypeTest {

    @Test
    void 문자열로_열거_타입_찾기() {
        SessionPayType paid = SessionPayType.search("FREE");

        assertThat(paid).isEqualTo(SessionPayType.FREE);
    }

    @Test
    void 찾기시_없는_문자열이면_예외() {
        assertThatThrownBy(
                () -> SessionPayType.search("free1")
        ).isInstanceOf(SessionException.class);
    }
}
