package nextstep.courses.domain;

import nextstep.courses.NotPendingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student pendingStudent;
    private Student approvedStudent;
    private Student rejectedStudent;

    @BeforeEach
    void setUp() {
        pendingStudent = new Student(1L, 1L, 1L, SessionApprovalStatus.PENDING);
        approvedStudent = new Student(2L, 2L, 2L, SessionApprovalStatus.APPROVED);
        rejectedStudent = new Student(3L, 3L, 3L, SessionApprovalStatus.REJECTED);
    }

    @Test
    void 승인상태_보류_아닐떄_예외발생() {
        assertThrows(NotPendingException.class, () -> approvedStudent.updateApprovalStatus(SessionApprovalStatus.APPROVED));
        assertThrows(NotPendingException.class, () -> rejectedStudent.updateApprovalStatus(SessionApprovalStatus.REJECTED));
    }

    @Test
    void 수강_거절() {
        pendingStudent.updateApprovalStatus(SessionApprovalStatus.REJECTED);
        assertEquals(pendingStudent, new Student(1L, 1L, 1L, SessionApprovalStatus.REJECTED));
    }

    @Test
    void 수강_승인() {
        pendingStudent.updateApprovalStatus(SessionApprovalStatus.APPROVED);
        assertEquals(pendingStudent, new Student(1L, 1L, 1L, SessionApprovalStatus.APPROVED));
    }
}