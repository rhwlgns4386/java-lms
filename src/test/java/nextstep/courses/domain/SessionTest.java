package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionTest {

    @Test
    @DisplayName("수강 등록 성공")
    void enroll_session_success() {
        Payment payment = new Payment("user01", 1L, 1L, 300000L, LocalDateTime.now(),true);
        Session session = new Session(LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            new SessionImage(500 * 1024, "jpg", 300, 200), false, SessionStatus.OPEN, 10, 0);

        assertThat(session.enroll(new Enrollment(payment))).isTrue();
    }

    @Test
    @DisplayName("수강 등록 실패_오픈하지 않은 경우")
    void enroll_session_fail_not_open() {
        Payment payment = new Payment("user01", 1L, 1L, 300000L, LocalDateTime.now(),true);
        Session session = new Session(LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            new SessionImage(500 * 1024, "jpg", 300, 200), false, SessionStatus.CLOSED, 10, 0);

        assertThatThrownBy(() -> {
            session.enroll(new Enrollment(payment));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수강 등록 실패_수강생이 초과한 경우")
    void enroll_session_fail_maxEnrollment() {
        Payment payment = new Payment("user01", 1L, 1L, 300000L, LocalDateTime.now(),true);
        Session session = new Session(LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            new SessionImage(500 * 1024, "jpg", 300, 200), false, SessionStatus.CLOSED, 1, 1);

        assertThatThrownBy(() -> {
            session.enroll(new Enrollment(payment));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
