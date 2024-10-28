package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class FreeSessionTest {
    private Image image;
    private SessionDate sessionDate;

    @BeforeEach
    void init() {
        this.image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImageProperty(300L, 200L));

        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        this.sessionDate = new SessionDate(start, end);
    }

    @Test
    @DisplayName("FreeSession 생성")
    void createFreeSessionTest() {
        SessionId sessionId = SessionId.of(1L, "TDD");
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        Assertions.assertThat(freeSession).isNotNull();
        Assertions.assertThat(freeSession.getSessionId()).isEqualTo(sessionId);
    }
}
