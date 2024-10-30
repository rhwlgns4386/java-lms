package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SessionTest {
    @Test
    public void 강의_생성() {
        Session session = new Session(1L, "테스트세션", "테스트 강의", null, "2024-01-01", "2024-01-02");

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("테스트세션");
        assertThat(session.getDescription()).isEqualTo("테스트 강의");
    }
}
