package nextstep.courses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {
    SessionDate sessionDate;
    @BeforeEach
    public void  setUp() {
        sessionDate = new SessionDate("20241029","202410231");
    }

    @Test
    public void 무료강의_최대_수강인원제한없음_테스트() {
        Premium premium = new Premium(false,0);
        Session session = new Session(premium, 0, SessionState.START,sessionDate);
        assertDoesNotThrow(() -> {
            session.requestSession(10000);
        });
    }

    @Test
    public void 유료강의_최대_수강인원제한_테스트() {
        Premium premium = new Premium(true,0);
        Session session = new Session(premium, 0, SessionState.START,sessionDate);
        assertThatIllegalArgumentException().isThrownBy(() -> {
            session.requestSession(10000);
        });
    }

    @Test
    public void 무료강의_강의금액불일치_성공테스트() {
        Premium premium = new Premium(false,10000);
        Session session = new Session(premium, 1, SessionState.START,sessionDate);
        assertDoesNotThrow(() -> {
            session.requestSession(9000);
        });
    }

    @Test
    public void 유료강의_강의금액불일치_테스트() {
        Premium premium = new Premium(true,10000);
        Session session = new Session(premium, 1, SessionState.START,sessionDate);
        assertThatIllegalArgumentException().isThrownBy(() -> {
            session.requestSession(9000);
        });
    }

    @Test
    public void 유료강의_강의금액일치_테스트() {
        Premium premium = new Premium(true,10000);
        Session session = new Session(premium, 1, SessionState.START,sessionDate);
        assertDoesNotThrow(() -> {
            session.requestSession(10000);
        });
    }

    @Test
    public void 강의신청은_강의상태아닐때_실패테스트() {
        Premium premium = new Premium(true,10000);
        Session session = new Session(premium,1,SessionState.READY,sessionDate);
        assertThatIllegalArgumentException().isThrownBy(() -> {
            session.requestSession(10000);
        });
    }

    @Test
    public void 강의신청_모집상태_성공테스트() {
        Premium premium = new Premium(true,10000);
        Session session = new Session(premium,1,SessionState.START,sessionDate);
        assertDoesNotThrow(() -> {
            session.requestSession(10000);
        });
    }

}
