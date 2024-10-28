package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class SessionsTest {

    @Test
    void 생성() {
        List<Session> sessionList = List.of(
            SessionTest.createPaidSession(3000L, SessionStatus.PREPARE),
            SessionTest.createFreeSession(SessionStatus.PREPARE)
        );
        Sessions sessions = new Sessions(sessionList);

        assertThat(sessions.size()).isEqualTo(2);
    }

    @Test
    void 해당하는_id의_Session을_반환한다() {
        List<Session> sessionList = List.of(
            SessionTest.createPaidSession(3000L, SessionStatus.PREPARE),
            SessionTest.createFreeSession(SessionStatus.PREPARE)
        );
        Sessions sessions = new Sessions(sessionList);

        Session session = sessions.getSession(2L);

        assertThat(session).isEqualTo(SessionTest.createFreeSession(SessionStatus.PREPARE));
    }
}
