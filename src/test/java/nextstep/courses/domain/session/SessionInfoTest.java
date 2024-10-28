package nextstep.courses.domain.session;

import nextstep.courses.dto.SessionPaymentInfo;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class SessionInfoTest {

    @Test
    void throw_exception_if_not_open_session() {
        SessionInfo preparing = new SessionInfo(
                "테스트",
                CoverImage.of("src/test/java/nextstep/courses/domain/session/file/image.png"),
                10000,
                SessionState.PREPARING);
        SessionInfo end = new SessionInfo(
                "테스트",
                CoverImage.of("src/test/java/nextstep/courses/domain/session/file/image.png"),
                10000,
                SessionState.END);

        assertThatIllegalStateException().isThrownBy(preparing::checkIsOpenSession);
        assertThatIllegalStateException().isThrownBy(end::checkIsOpenSession);
    }

    @Test
    void session_payment_info() {
        SessionInfo sessionInfo = new SessionInfo(
                "테스트",
                CoverImage.of("src/test/java/nextstep/courses/domain/session/file/image.png"),
                10000);
        SessionPaymentInfo sessionPaymentInfo = sessionInfo.sessionPaymentInfo(1L);

        assertThat(sessionPaymentInfo.getSessionFee()).isEqualTo(10000);
        assertThat(sessionPaymentInfo.getSessionId()).isEqualTo(1L);
    }

    @Test
    void valid_payment() {
        SessionInfo sessionInfo = new SessionInfo(
                "테스트",
                CoverImage.of("src/test/java/nextstep/courses/domain/session/file/image.png"),
                10000);

        assertThat(sessionInfo.isValidPayment(
                new Payment("테스트", 1L, NsUserTest.JAVAJIGI.getId(), 10000L), 1L)
        ).isTrue();
    }

    @Test
    void invalid_payment() {
        SessionInfo sessionInfo = new SessionInfo(
                "테스트",
                CoverImage.of("src/test/java/nextstep/courses/domain/session/file/image.png"),
                10000);

        assertThat(sessionInfo.isValidPayment(
                new Payment("테스트", 1L, NsUserTest.JAVAJIGI.getId(), 1000L), 1L)
        ).isFalse();
        assertThat(sessionInfo.isValidPayment(
                new Payment("테스트", 10L, NsUserTest.JAVAJIGI.getId(), 10000L), 1L)
        ).isFalse();
    }
}
