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
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        SessionManager sessionManager = new SessionManager();

        sessionManager.addSessions(freeSession);

        Assertions.assertThat(sessionManager.getSessions()).hasSize(1);
    }

    @Test
    @DisplayName("SessionManager에 FreeSession 추가")
    void addFreeSessionTest() {
        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImageProperty(300L, 200L));
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);
        SessionId sessionId = SessionId.of(1L, "TDD");
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        SessionManager sessionManager = new SessionManager();

        sessionManager.addFreeSession(sessionDate, image, sessionId);

        Session foundSession = sessionManager.findBySessionId(sessionId);
        Assertions.assertThat(foundSession).isNotNull();
        Assertions.assertThat(foundSession.getSessionType()).isEqualTo(SessionType.FREE);
    }

    @Test
    @DisplayName("SessionManager에 PaidSession 추가")
    void addPaidSessionTest() {
        Image image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImageProperty(300L, 200L));
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);

        SessionId sessionId = SessionId.of(1L, "TDD");
        Integer capacity = 10;
        Long price = 200_000L;

        PaidSession paidSession = new PaidSession(sessionDate, image, sessionId, capacity, price);

        SessionManager sessionManager = new SessionManager();

        sessionManager.addPaidSession(sessionDate, image, sessionId, capacity, price);

        Session foundSession = sessionManager.findBySessionId(sessionId);
        Assertions.assertThat(foundSession).isNotNull();
        Assertions.assertThat(foundSession.getSessionType()).isEqualTo(SessionType.PAID);
    }
}
