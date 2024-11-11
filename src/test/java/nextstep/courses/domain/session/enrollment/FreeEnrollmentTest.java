package nextstep.courses.domain.session.enrollment;

import nextstep.courses.CannotApplyException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FreeEnrollmentTest {
    private Students students;
    private Payment payment1;

    @BeforeEach
    void beforeSet() {
        students = new Students(0);
        payment1 = new Payment("1", 1L, 1L, 0L);
    }

    @Test
    @DisplayName("무료 강의인 경우 최대 수강 인원과 상관없이 수강신청이 정상적으로 진행되어야 한다")
    void enroll_정상케이스() {
        Enrollment enrollment = new FreeEnrollment(Status.RECRUIT, students);

        enrollment.enroll(NsUserTest.JAVAJIGI, payment1);

        Assertions.assertThat(students.countOfStudent()).isEqualTo(1);
    }

    @Test
    @DisplayName("현재 모집중인 강의가 아니라면 수강신청이 불가능하다")
    void enroll_실패케이스_현재_모집중인_강의가_아닌_경우() {
        Enrollment enrollment = new FreeEnrollment(Status.PREPARE, students);

        Assertions.assertThatThrownBy(() -> {
            enrollment.enroll(NsUserTest.JAVAJIGI, payment1);
        }).isInstanceOf(CannotApplyException.class);
    }

    @Test
    @DisplayName("이미 등록된 학생인 경우 수강신청이 불가능하다")
    void enroll_실패케이스_이미_등록된_학생인_경우() {
        Enrollment enrollment = new FreeEnrollment(Status.RECRUIT, students);
        enrollment.enroll(NsUserTest.JAVAJIGI, payment1);

        Payment payment2 = new Payment("2", 2L, 1L, 0L);

        Assertions.assertThatThrownBy(() -> {
            enrollment.enroll(NsUserTest.JAVAJIGI, payment2);
        }).isInstanceOf(CannotApplyException.class);

    }
}