package nextstep.courses.domain;

import nextstep.courses.CannotOpenException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionTest {

    public static final LocalDate NOW = LocalDate.now();
    public static final SessionPeriod PERIOD = new SessionPeriod(NOW, NOW.plusDays(1L));
    public static final SessionCoverImage COVER_IMAGE = new SessionCoverImage(500 * 1024, "jpg", 300, 200);
    public static final SessionAmount AMOUNT = new SessionAmount(100_000L);

    public static final NsUser NS_USER = new NsUser(0L, "", "", "", "");
    public static final SessionApply APPLY = new SessionApply(0L, new SessionAmount(100_000L), 0);
    public static final SessionApply MAX_PERSONNEL_APPLY = new SessionApply(0L, new SessionAmount(100_000L), 1);
    public static final SessionApply DISMATCH_ADD_INFO = new SessionApply(0L, new SessionAmount(10_000L), 1);

    private Session paidSession;
    private Session preparingSession;
    private Session closedSession;

    @BeforeEach
    void setUp() {
        preparingSession = Session.paidSession(0L, 0L, PERIOD, COVER_IMAGE, AMOUNT, 1, SessionStatus.PREPARING);
        paidSession = Session.paidSession(0L, 0L, PERIOD, COVER_IMAGE, AMOUNT, 1, SessionStatus.RECRUITING);
        closedSession = Session.paidSession(0L, 0L, PERIOD, COVER_IMAGE, AMOUNT, 1, SessionStatus.CLOSED);
    }

    @Test
    void 세션_오픈() {
        preparingSession.open();
        assertEquals(preparingSession, paidSession);
        assertThrows(CannotOpenException.class, () -> paidSession.open());
        assertThrows(CannotOpenException.class, () -> closedSession.open());
    }

    @Test
    void 세션_생성() {
        Session session = Session.from(new SessionCreate(0L, NOW, NOW.plusDays(1L), 500 * 1024, "jpg", 300, 200, 100_000L, 50));
        assertEquals(session, new Session(0L, PERIOD, COVER_IMAGE, SessionFeeType.PAID, AMOUNT, 50, SessionStatus.PREPARING));
    }

    @Test
    void 유료강의_최대인원초과_예외발생() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> paidSession.apply(NS_USER, MAX_PERSONNEL_APPLY));
        assertEquals(exception.getMessage(), "Max personnel exceeded.");
    }

    @Test
    void 결제금액_수강료_불일치_예외발생() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> paidSession.apply(NS_USER, DISMATCH_ADD_INFO));
        assertEquals(exception.getMessage(), "Payment amount does not match.");
    }

    @Test
    void 수강상태_모집중_아닐때_예외발생() {
        Session preparingSession = Session.freeSession(0L, 0L, PERIOD, COVER_IMAGE, SessionStatus.PREPARING);
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> preparingSession.apply(NS_USER, APPLY));
        assertEquals(exception1.getMessage(), "Session is not recruiting.");

        Session closedSession = Session.freeSession(0L, 0L, PERIOD, COVER_IMAGE, SessionStatus.CLOSED);
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> closedSession.apply(NS_USER, APPLY));
        assertEquals(exception2.getMessage(), "Session is not recruiting.");
    }

    @Test
    void 수강신청() {
        Student student = paidSession.apply(NS_USER, APPLY);
        assertEquals(student, new Student(0L, 0L));
    }
}