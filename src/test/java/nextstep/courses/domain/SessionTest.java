package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionTest {

    public static final SessionPeriod SESSION_PERIOD = new SessionPeriod(LocalDate.now(), LocalDate.now().plusDays(1L));
    public static final SessionCoverImage SESSION_COVER_IMAGE = new SessionCoverImage(500 * 1024, "jpg", 300, 200);
    public static final Session SESSION = new Session(0L, SESSION_PERIOD, SESSION_COVER_IMAGE, SessionFeeType.FREE, 100_000L, 0, SessionStatus.RECRUITING);
    public static final Payment PAYMENT = new Payment("", 0L, 0L, 100_000L);

    @Test
    void 유료강의_최대인원초과_예외발생() {
        Session session = new Session(0L, SESSION_PERIOD, SESSION_COVER_IMAGE, SessionFeeType.PAID, 100_000L, 1, SessionStatus.RECRUITING);
        session.add(PAYMENT);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> session.add(PAYMENT));
        assertEquals(exception.getMessage(), "Max personnel exceeded.");
    }

    @Test
    void 결제금액_수강료_불일치_예외발생() {
        Session session = new Session(0L, SESSION_PERIOD, SESSION_COVER_IMAGE, SessionFeeType.PAID, 100_00L, 10, SessionStatus.RECRUITING);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> session.add(PAYMENT));
        assertEquals(exception.getMessage(), "Payment amount does not match.");
    }

    @Test
    void 수강상태_모집중_아닐때_예외발생() {
        Session prepareingSession = new Session(0L, SESSION_PERIOD, SESSION_COVER_IMAGE, SessionFeeType.FREE, 0L, 0, SessionStatus.PREPARING);
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> prepareingSession.add(PAYMENT));
        assertEquals(exception1.getMessage(), "Session is not recruiting.");

        Session closedSession = new Session(0L, SESSION_PERIOD, SESSION_COVER_IMAGE, SessionFeeType.FREE, 0L, 0, SessionStatus.CLOSED);
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> closedSession.add(PAYMENT));
        assertEquals(exception2.getMessage(), "Session is not recruiting.");
    }

    @Test
    void 수강신청() {
        assertEquals(SESSION.sizeNsUsers(), 0);
        SESSION.add(PAYMENT);
        assertEquals(SESSION.sizeNsUsers(), 1);
    }
}