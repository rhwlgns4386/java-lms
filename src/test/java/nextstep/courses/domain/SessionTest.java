package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SessionTest {
    @Test
    @DisplayName("강의 class 생성")
    void createSessionTest() {
        Long id = 1L;
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        SessionType type = SessionType.FREE;
        SessionStatus status = SessionStatus.PREPARING;

        Session session = new Session(id, name, startDate, endDate, type, status);

        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getId()).isEqualTo(id);
    }
}
