package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SessionTest {
    @Test
    public void 강의_생성() {
        Session session = new Session(1L, "테스트세션", "테스트 강의", null,  true, 300, "2021-08-01", "2021-08-31");

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("테스트세션");
        assertThat(session.getDescription()).isEqualTo("테스트 강의");
        assertThat(session.isFree()).isTrue();
        assertThat(session.getSessionFee()).isEqualTo(300);
        assertThat(session.getStartDate()).isEqualTo("2021-08-01");
        assertThat(session.getEndDate()).isEqualTo("2021-08-31");
    }
}
