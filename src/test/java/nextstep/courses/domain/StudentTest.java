package nextstep.courses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

public class StudentTest {
    private Student student;
    private Session session1;
    private Session session2;

    @BeforeEach
    void setup() {
        student = new Student(1L, "user123", "password", "John Doe", "john@example.com", LocalDateTime.now(), LocalDateTime.now());
        session1 = new Session(1L, "test1", "test", null, true, 100, "2021-08-01", "2021-08-31");
        session2 = new Session(2L, "test2", "test", null, true, 200, "2021-08-01", "2021-08-31");
    }

    @Test
    void 세션_등록_세션_목록_추가_및_결제_금액_증가() {
        student.registerSession(session1);
        assertEquals(1, student.getSessionCount());
        assertEquals(100, student.getPaymentAmount());

        student.registerSession(session2);
        assertEquals(2, student.getSessionCount());
        assertEquals(300, student.getPaymentAmount());
    }

}
