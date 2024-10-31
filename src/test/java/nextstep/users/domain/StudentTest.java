package nextstep.users.domain;

import nextstep.sessions.Session;
import nextstep.users.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {
    private Student student;
    private Session session1;
    private Session session2;

    @BeforeEach
    void setup() {
        student = new Student(1L, "user123", "password", "John Doe", "john@example.com", LocalDateTime.now(), LocalDateTime.now());
        session1 = new Session.SessionBuilder(1L, "test1", "test", null, LocalDateTime.now(), LocalDateTime.now())
                .isFree(true)
                .maxStudentCount(100)
                .sessionFee(100L)
                .build();
        session2 = new Session.SessionBuilder(2L, "test1", "test", null, LocalDateTime.now(), LocalDateTime.now())
                .isFree(true)
                .maxStudentCount(100)
                .sessionFee(100L)
                .build();
    }

    @Test
    void 세션_등록_세션_목록_추가_및_결제_금액_증가() {
        student.registerSession(session1);
        assertEquals(1, student.getSessionCount());

        student.registerSession(session2);
        assertEquals(2, student.getSessionCount());
    }

}
