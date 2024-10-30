package nextstep.courses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionTest {

    public static final SessionPeriod PERIOD = new SessionPeriod(LocalDate.now(), LocalDate.now().plusDays(1L));
    public static final SessionCoverImage COVER_IMAGE = new SessionCoverImage(500 * 1024, "jpg", 300, 200);
    public static final SessionAmount AMOUNT = new SessionAmount(100_000L);

    public static final SessionAddInfo ADD_INFO = new SessionAddInfo(0L, 100_000L);
    public static final SessionAddInfo DISMATCH_ADD_INFO = new SessionAddInfo(0L, 10_000L);

    private Session paidSession;

    @BeforeEach
    void setUp() {
        paidSession = Session.paidSession(0L, PERIOD, COVER_IMAGE, AMOUNT, 1, SessionStatus.RECRUITING);
    }

    @Test
    void 유료강의_최대인원초과_예외발생() {
        paidSession.apply(ADD_INFO);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> paidSession.apply(ADD_INFO));
        assertEquals(exception.getMessage(), "Max personnel exceeded.");
    }

    @Test
    void 결제금액_수강료_불일치_예외발생() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> paidSession.apply(DISMATCH_ADD_INFO));
        assertEquals(exception.getMessage(), "Payment amount does not match.");
    }

    @Test
    void 수강상태_모집중_아닐때_예외발생() {
        Session preparingSession = Session.freeSession(0L, PERIOD, COVER_IMAGE, SessionStatus.PREPARING);
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> preparingSession.apply(ADD_INFO));
        assertEquals(exception1.getMessage(), "Session is not recruiting.");

        Session closedSession = Session.freeSession(0L, PERIOD, COVER_IMAGE, SessionStatus.CLOSED);
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> closedSession.apply(ADD_INFO));
        assertEquals(exception2.getMessage(), "Session is not recruiting.");
    }

    @Test
    void 수강신청() {
        assertEquals(paidSession.sizeNsUsers(), 0);
        paidSession.apply(ADD_INFO);
        assertEquals(paidSession.sizeNsUsers(), 1);
    }
}