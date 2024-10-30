package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class FreeSessionTest {
    private Image image;
    private SessionDate sessionDate;
    private SessionId sessionId;

    @BeforeEach
    void init() {
        this.image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImagePixel(300L, 200L));

        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        this.sessionDate = new SessionDate(start, end);
        this.sessionId = SessionId.of(1L, "TDD");
    }

    @Test
    @DisplayName("FreeSession 생성")
    void createFreeSessionTest() {
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        Assertions.assertThat(freeSession).isNotNull();
        Assertions.assertThat(freeSession.getSessionId()).isEqualTo(sessionId);
    }

    @Test
    @DisplayName("FreeSession 수강 신청")
    void registerFreeSessionTest() {
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        freeSession.open();

        freeSession.register();

        Assertions.assertThat(freeSession.getSessionId()).isEqualTo(sessionId);
        Assertions.assertThat(freeSession.getSessionStatus()).isEqualTo(SessionStatus.RECRUITING);
    }

    @Test
    @DisplayName("FreeSession 수강 신청 - 모집중이 아닌 강좌 신청 체크")
    void checkRegisterNotOpenFreeSessionTest() {
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        Assertions.assertThatThrownBy(() -> freeSession.register())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("강의 모집중")
    void openFreeSessionTest() {
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        freeSession.open();

        Assertions.assertThat(freeSession.getSessionStatus()).isEqualTo(SessionStatus.RECRUITING);
    }

    @Test
    @DisplayName("강의 종료")
    void closeFreeSessionTest() {
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        freeSession.close();

        Assertions.assertThat(freeSession.getSessionStatus()).isEqualTo(SessionStatus.CLOSE);
    }
}
