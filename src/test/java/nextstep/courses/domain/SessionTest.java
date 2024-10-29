package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {

    @Test
    public void 무료강의_최대_수강인원제한없음_테스트() {
        Premium premium = new Premium(false,0);
        Session session = new Session(premium, 0);
        assertDoesNotThrow(() -> {
            session.requestSession(10000);
        });
    }

    @Test
    public void 유료강의_최대_수강인원제한_테스트() {
        Premium premium = new Premium(true,0);
        Session session = new Session(premium, 0);
        assertThatIllegalArgumentException().isThrownBy(() -> {
            session.requestSession(10000);
        });
    }

    @Test
    public void 무료강의_강의금액불일치_성공테스트() {
        Premium premium = new Premium(false,10000);
        Session session = new Session(premium, 1);
        assertDoesNotThrow(() -> {
            session.requestSession(9000);
        });
    }

    @Test
    public void 유료강의_강의금액불일치_테스트() {
        Premium premium = new Premium(true,10000);
        Session session = new Session(premium, 1);
        assertThatIllegalArgumentException().isThrownBy(() -> {
            session.requestSession(9000);
        });
    }

    @Test
    public void 유료강의_강의금액일치_테스트() {
        Premium premium = new Premium(true,10000);
        Session session = new Session(premium, 1);
        assertDoesNotThrow(() -> {
            session.requestSession(10000);
        });
    }

}
