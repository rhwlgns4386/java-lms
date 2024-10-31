package nextstep.sessions.domain;

import nextstep.sessions.Session;
import nextstep.sessions.Sessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionsTest {

    private Sessions sessions;
    private Session session1;
    private Session session2;

    @BeforeEach
    public void setUp() {
        session1 = new Session.SessionBuilder(1L, "테스트세션1", "테스트 강의1", null, LocalDate.parse("2024-01-01").atStartOfDay(), LocalDate.parse("2024-01-02").atStartOfDay()).build();
        session2 = new Session.SessionBuilder(2L, "테스트세션2", "테스트 강의2", null, LocalDate.parse("2024-01-01").atStartOfDay(), LocalDate.parse("2024-01-02").atStartOfDay()).build();
        sessions = new Sessions(new ArrayList<>(List.of(session1)));
    }

    @Test
    public void 세션_추가() {
        sessions.addSession(session2);

        assertThat(sessions.getSessions()).containsExactly(session1, session2);
    }

    @Test
    public void 세션_포함_여부_확인() {
        assertThat(sessions.contains(session1)).isTrue();
        assertThat(sessions.contains(session2)).isFalse();
    }

    @Test
    public void 세션_유효성_검사_예외_발생() {
        assertThatThrownBy(() -> sessions.validateSession(session2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 코스에 해당하는 강의가 아닙니다.");
    }
}
