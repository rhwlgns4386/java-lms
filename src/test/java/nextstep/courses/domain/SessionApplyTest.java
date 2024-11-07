package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionApplyTest {

    public static final Student ONE = new Student(1L, 1L);
    public static final Student TWO = new Student(2L, 2L);

    public static final SessionApply PREPARING = new SessionApply(10, SessionStatus.PREPARING, List.of());
    public static final SessionApply RECRUITING = new SessionApply(10, SessionStatus.RECRUITING, List.of());
    public static final SessionApply CLOSED = new SessionApply(10, SessionStatus.CLOSED, List.of());

    public static final SessionApply MAX = new SessionApply(1, SessionStatus.RECRUITING, List.of(ONE));
    public static final SessionApply DUPLICATE = new SessionApply(2, SessionStatus.RECRUITING, List.of(ONE));

    @Test
    void 중복신청_예외발생() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> DUPLICATE.apply(ONE));
        assertEquals(exception.getMessage(), "Student is already in the list.");
    }

    @Test
    void 최대인원초과_예외발생() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> MAX.apply(TWO));
        assertEquals(exception.getMessage(), "Max personnel exceeded.");
    }

    @Test
    void 수강상태_모집중_아닐때_예외발생() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> PREPARING.apply(ONE));
        assertEquals(exception1.getMessage(), "Session is not recruiting.");
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> CLOSED.apply(ONE));
        assertEquals(exception2.getMessage(), "Session is not recruiting.");
    }

    @Test
    void 수강신청() {
        RECRUITING.apply(ONE);
        assertEquals(RECRUITING, new SessionApply(10, SessionStatus.RECRUITING, List.of(ONE)));
    }
}