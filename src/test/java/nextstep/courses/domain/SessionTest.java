package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {

    @Test
    void 유료강의_수강신청_성공() {
        PaidSessionStrategy paidSessionStrategy = new PaidSessionStrategy(10, 10000);
        Session session = new Session(1000000, "jpg", 300, 200, SessionState.RECRUITING, paidSessionStrategy);

        boolean expected = session.applyForCourse(10000);

        assertThat(expected).isEqualTo(true);
    }

    @Test
    void 유료강의_수강신청_실패() {
        SessionStrategy paidSessionStrategy = new PaidSessionStrategy(10, 10000);
        Session session = new Session(1000000, "jpg", 300, 200, SessionState.RECRUITING, paidSessionStrategy);

        boolean expected = session.applyForCourse(1000);

        assertThat(expected).isEqualTo(false);
    }

    @Test
    void 무료강의_수강신청_성공() {
        SessionStrategy freeSessionStrategy = new FreeSessionStrategy();
        Session session = new Session(1000000, "jpg", 300, 200, SessionState.RECRUITING, freeSessionStrategy);

        boolean expected = session.applyForCourse(1000);

        assertThat(expected).isEqualTo(true);
    }
}
