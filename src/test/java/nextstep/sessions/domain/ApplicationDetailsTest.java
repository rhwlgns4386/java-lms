package nextstep.sessions.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ApplicationDetailsTest {
    private static Long SESSION_ID = Long.valueOf(123);
    private static Long NS_USER_ID = Long.valueOf(12345);
    @Test
    void create() {
        assertThat(new ApplicationDetails().size()).isEqualTo(0);

    }


    @Test
    void add() {
        ApplicationDetails details = new ApplicationDetails();
        details.add(ApplicationDetail
                .ofNewInstance(SESSION_ID, NS_USER_ID));
        assertThat(details.size()).isEqualTo(1);

    }

    @Test
    void canApply() {
        ApplicationDetails details = new ApplicationDetails();
        details.add(ApplicationDetail
                .ofNewInstance(SESSION_ID, NS_USER_ID));
        assertThatThrownBy(() -> details.canApply(SESSION_ID, NS_USER_ID)).isInstanceOf(RuntimeException.class);
    }
}
