package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.sessions.Session;
import nextstep.sessions.SessionState;
import nextstep.users.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CourseTest {
    private Course course;
    private Session session;
    private Session freeSession;
    private Student student;

    @BeforeEach
    void setUp() {
        course = new Course("자바지기", 1L, 1);
        session = new Session.SessionBuilder(
                1L,
                "자바지기 1주차",
                "자바지기 1주차 내용",
                null,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2)
        ).state(SessionState.OPEN)
                .isFree(false)
                .maxStudentCount(30)
                .sessionFee(10000L)
                .build();

        freeSession = new Session.SessionBuilder(
                3L,
                "자바지기 무료 세션",
                "무료 세션 내용",
                null,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2)
        ).state(SessionState.OPEN)
                .isFree(true)
                .build();

        student = new Student(1L, "javajigi", "password", "자바지기", "test@test.com");
    }

    @Test
    public void 코스는_기수를_가진다() {
        assertThat(course.getCohort()).isEqualTo(1);
    }

    @Test
    public void 코스에_세션을_추가한다() {
        Course course = new Course("자바지기", 1L, 1);
        course.addSession(session);

        assertThat(course.getSessionSize()).isEqualTo(1);
        assertThat(course.contains(session)).isTrue();
    }

    @Test
    public void 학생이_세션에_등록되고_결제가_처리된다() {
        course.addSession(session);
        Payment payment = course.processPayment(student, 10000L, session);

        assertAll(
                () -> assertThat(session.contains(student)).isTrue(),
                () -> assertThat(payment).isNotNull(),
                () -> assertThat(payment.getAmount()).isEqualTo(10000L),
                () -> assertThat(payment.getCourseId()).isEqualTo(course.getId()),
                () -> assertThat(payment.getSessionId()).isEqualTo(session.getId()),
                () -> assertThat(payment.getStudentId()).isEqualTo(student.getId())
        );
    }

    @Test
    public void 등록기간이_아닌_세션에_등록하려면_예외가_발생한다() {
        session.setClose();
        course.addSession(session);

        assertThatThrownBy(() -> course.processPayment(student, 10000L, session))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("아직 접수 기간이 아닙니다.");
    }

    @Test
    public void 무료세션에_등록한다() {
        course.addSession(freeSession);
        Payment payment = course.processPayment(student, 0L, freeSession);

        assertThat(payment).isNotNull();
        assertThat(payment.getAmount()).isEqualTo(0L);
    }
}
