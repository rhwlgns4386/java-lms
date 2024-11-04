package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SessionTest {
    @DisplayName("세션 준비중")
    @Test
    void 세션_준비중() {
        Session session = new FreeSession("세션", LocalDate.of(2024, 10, 28),
                LocalDate.of(2024, 11, 28), new SessionImage(600, 300, 200, "jpg"));
        session.readySession();
        assertThat(session.getSessionStatus()).isEqualTo(SessionStatus.READY);
    }

    @DisplayName("세션 모집중")
    @Test
    void 세션_모집중() {
        Session session = new FreeSession("세션", LocalDate.of(2024, 10, 28),
                LocalDate.of(2024, 11, 28), new SessionImage(600, 300, 200, "jpg"));
        session.openSession();
        assertThat(session.getSessionStatus()).isEqualTo(SessionStatus.OPEN);
    }

    @DisplayName("세션 종료")
    @Test
    void 세션_종료() {
        Session session = new FreeSession("세션", LocalDate.of(2024, 10, 28),
                LocalDate.of(2024, 11, 28), new SessionImage(600, 300, 200, "jpg"));
        session.closeSession();
        assertThat(session.getSessionStatus()).isEqualTo(SessionStatus.CLOSED);
    }

    @DisplayName("세션 오픈이 안되어서 수강생 등록 실패")
    @Test
    void 수강생_등록_에러() {
        Session session = new FreeSession("세션", LocalDate.of(2024, 10, 28),
                LocalDate.of(2024, 11, 28), new SessionImage(600, 300, 200, "jpg"));
        assertThatThrownBy(() -> {
            session.register(JAVAJIGI, new Payment());
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
