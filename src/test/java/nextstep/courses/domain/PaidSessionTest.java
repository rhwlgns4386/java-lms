package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PaidSessionTest {
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
    @DisplayName("PaidSession 생성")
    void createPaidSessionTest() {
        SessionId sessionId = SessionId.of(1L, "TDD");
        Integer capacity = 10;
        Long price = 200_000L;

//        PaidSession paidSession = new PaidSession(sessionDate, image, sessionId, capacity, price);

//        Assertions.assertThat(paidSession).isNotNull();
//        Assertions.assertThat(paidSession.getSessionId()).isEqualTo(sessionId);
    }
}
