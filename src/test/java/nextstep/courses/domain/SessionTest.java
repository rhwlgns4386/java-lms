package nextstep.courses.domain;

import nextstep.courses.CannotOpenException;
import nextstep.courses.NotPendingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionTest {

    public static final LocalDate NOW = LocalDate.now();
    public static final SessionPeriod PERIOD = new SessionPeriod(NOW, NOW.plusDays(1L));
    public static final SessionAmount AMOUNT = new SessionAmount(100_000L);

    private Session pendingSession;
    private Session approvedSession;
    private Session rejectedSession;
    private Session paidSession;
    private Session preparingSession;
    private Session closedSession;

    @BeforeEach
    void setUp() {
        pendingSession = Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PREPARING, SessionRecruitment.RECRUITING, SessionApprovalStatus.PENDING);
        approvedSession = Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PREPARING, SessionRecruitment.RECRUITING, SessionApprovalStatus.APPROVED);
        rejectedSession = Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PREPARING, SessionRecruitment.RECRUITING, SessionApprovalStatus.REJECTED);
        preparingSession = Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PREPARING, SessionRecruitment.RECRUITING, SessionApprovalStatus.PENDING);
        paidSession = Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PROGRESSING, SessionRecruitment.RECRUITING, SessionApprovalStatus.PENDING);
        closedSession = Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.CLOSED, SessionRecruitment.RECRUITING, SessionApprovalStatus.PENDING);
    }

    @Test
    void 승인상태_보류_아닐떄_예외발생() {
        assertThrows(NotPendingException.class, () -> approvedSession.updateApprovalStatus(SessionApprovalStatus.APPROVED));
        assertThrows(NotPendingException.class, () -> rejectedSession.updateApprovalStatus(SessionApprovalStatus.REJECTED));
    }

    @Test
    void 수강_거절() {
        pendingSession.updateApprovalStatus(SessionApprovalStatus.REJECTED);
        assertEquals(pendingSession, Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PREPARING, SessionRecruitment.RECRUITING, SessionApprovalStatus.REJECTED));
    }

    @Test
    void 수강_승인() {
        pendingSession.updateApprovalStatus(SessionApprovalStatus.APPROVED);
        assertEquals(pendingSession, Session.paidSession(0L, 0L, PERIOD, AMOUNT, 1, SessionProgressStatus.PREPARING, SessionRecruitment.RECRUITING, SessionApprovalStatus.PENDING));
    }

    @Test
    void 수강신청() {
        List<Student> students = List.of(new Student(1L, 1L));
        assertEquals(paidSession.sessionApply(students), new SessionApply(1, SessionRecruitment.RECRUITING, SessionProgressStatus.PROGRESSING, List.of(new Student(1L, 1L))));
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
        Session session = Session.from(new SessionCreate(0L, NOW, NOW.plusDays(1L), 100_000L, 50));
        assertEquals(session, new Session(0L, PERIOD, SessionFeeType.PAID, AMOUNT, 50, SessionProgressStatus.PREPARING, SessionRecruitment.RECRUITING, SessionApprovalStatus.PENDING));
    }
}