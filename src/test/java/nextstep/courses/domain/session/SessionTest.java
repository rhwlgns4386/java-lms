package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import nextstep.courses.domain.PaymentType;
import nextstep.courses.domain.image.SessionImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    void 무료_강의_생성() {
        Session freeSession = generateFreeSession();
        assertThat(freeSession)
                .extracting("title", "paymentType")
                .containsExactly("클린코드", PaymentType.FREE);
    }

    @Test
    void 유료_강의_생성() {
        Session paidSession = generatePaidSession();
        assertThat(paidSession)
                .extracting("title", "paymentType")
                .containsExactly("클린코드", PaymentType.PAID);
    }

    @Test
    void 무료_강의_신청() throws SessionException {
        Session freeSession = generateFreeSession();
        NsUser user = NsUserTest.JAVAJIGI;
        freeSession.subscribe(user, null);

        assertThat(freeSession.getSubscribeCount()).isEqualTo(1);
    }

    @Test
    void 강의_신청_상태_예외처리() {
        Session freeSession = new Session(1L, generateSessionDate(), generateSessionImage(), PaymentType.FREE,
                SessionStatus.READY, Integer.MAX_VALUE, "클린코드",
                0);
        NsUser user = NsUserTest.JAVAJIGI;
        assertThatThrownBy(() -> freeSession.subscribe(user, null))
                .isInstanceOf(SessionException.class);
    }

    @Test
    void 강의_중복신청_예외처리() throws SessionException {
        Session freeSession = generateFreeSession();
        NsUser user = NsUserTest.JAVAJIGI;
        freeSession.subscribe(user, null);
        assertThatThrownBy(() -> freeSession.subscribe(user, null))
                .isInstanceOf(SessionException.class);
    }

    @Test
    void 유료_강의_신청() throws SessionException {
        Session paidSession = generatePaidSession();
        NsUser user = NsUserTest.JAVAJIGI;
        paidSession.subscribe(user, generatePayment(paidSession, user));

        assertThat(paidSession.getSubscribeCount()).isEqualTo(1);
    }

    @Test
    void 유료_강의_금액_예외처리() {
        Session paidSession = generatePaidSession();
        NsUser user = NsUserTest.JAVAJIGI;
        Payment payment = new Payment("id", paidSession.getId(), user.getId(), 10000L);

        assertThatThrownBy(() -> paidSession.subscribe(user, payment))
                .isInstanceOf(SessionException.class);
    }

    @Test
    void 유료_강의_신청_정원_예외처리() throws SessionException {
        Session paidSession = new Session(1L, generateSessionDate(), generateSessionImage(), PaymentType.PAID,
                SessionStatus.RECRUITING, 1, "클린코드",
                600000);
        NsUser user1 = NsUserTest.JAVAJIGI;
        NsUser user2 = NsUserTest.SANJIGI;
        paidSession.subscribe(user1, generatePayment(paidSession, user1));

        assertThatThrownBy(() -> paidSession.subscribe(user2, generatePayment(paidSession, user2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Session generateFreeSession() {
        return new Session(1L, generateSessionDate(), generateSessionImage(), PaymentType.FREE,
                generateFreeSubscription(), "클린코드",
                0);
    }

    private Session generatePaidSession() {
        return new Session(1L, generateSessionDate(), generateSessionImage(), PaymentType.PAID,
                generatePaidSubscription(), "클린코드",
                600000);
    }

    private SessionImage generateSessionImage() {
        return new SessionImage("next.jpg", 300, 200, 1000);
    }

    private SessionDate generateSessionDate() {
        return new SessionDate(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31));
    }

    private Payment generatePayment(Session session, NsUser user) {
        return new Payment("id", session.getId(), user.getId(), 600000L);
    }

    private Subscription generatePaidSubscription() {
        return new Subscription(SessionStatus.RECRUITING, 30);
    }

    private Subscription generateFreeSubscription() {
        return new Subscription(SessionStatus.RECRUITING, Integer.MAX_VALUE);
    }
}
