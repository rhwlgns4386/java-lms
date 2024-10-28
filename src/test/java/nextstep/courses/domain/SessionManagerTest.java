package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SessionManagerTest {
    @Test
    @DisplayName("SessionManager 생성")
    void createSessionManagerTest() {
        SessionManager sessionManager = new SessionManager();

        Assertions.assertThat(sessionManager).isNotNull();
    }

    @Test
    @DisplayName("SessionManager에 Session 추가")
    void addSessionsTest() {
        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImageProperty(300L, 200L));
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);
        SessionId sessionId = SessionId.of(1L, "TDD");
        FreeSession freeSession = new FreeSession(sessionDate, image, sessionId);

        SessionManager sessionManager = new SessionManager();

        sessionManager.addSessions(freeSession);

        Assertions.assertThat(sessionManager.getSessions()).hasSize(1);
    }


}
