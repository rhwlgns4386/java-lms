package nextstep.courses.domain;

import nextstep.courses.CannotOpenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionTest {

    public static final LocalDate NOW = LocalDate.now();
    public static final SessionPeriod PERIOD = new SessionPeriod(NOW, NOW.plusDays(1L));
    public static final SessionCoverImage COVER_IMAGE = new SessionCoverImage(500 * 1024, "jpg", 300, 200);
    public static final SessionAmount AMOUNT = new SessionAmount(100_000L);

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
    void 수강신청() {
        List<Student> students = List.of(new Student(1L, 1L));
        assertEquals(paidSession.sessionApply(students), new SessionApply(1, SessionStatus.RECRUITING, List.of(new Student(1L, 1L))));
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
}