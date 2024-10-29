package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionStrategyTest {

    @Test
    void create() {
         SessionStrategy expected = new PaidSessionStrategy(10, 10000);

         assertThat(expected).isEqualTo(new PaidSessionStrategy(10, 10000));
    }

    @Test
    void 유료_수강신청() {
        SessionStrategy sessionStrategy = new PaidSessionStrategy(10, 10000);

        boolean expected = sessionStrategy.applyForCourse(SessionState.RECRUITING, 10000);

        assertThat(expected).isEqualTo(true);
    }

    @Test
    void 무료_수강신청() {
        SessionStrategy sessionStrategy = new FreeSessionStrategy();

        boolean expected = sessionStrategy.applyForCourse(SessionState.RECRUITING, 10000);

        assertThat(expected).isEqualTo(true);
    }
}
