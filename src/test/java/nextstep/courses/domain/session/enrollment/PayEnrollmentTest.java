package nextstep.courses.domain.session.enrollment;

import nextstep.courses.CannotApplyException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.session.enrollment.PriceTest.createPayPrice;

class PayEnrollmentTest {
    private Students students;
    private Price payPrice;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void beforeSet() {
        students = new Students(2);
        payPrice = createPayPrice(800_000L);
        payment1 = new Payment("1", 1L, 1L, 800_000L);
        payment2 = new Payment("2", 2L, 2L, 800_000L);
    }

    @Test
    void enroll_정상케이스() {
        Enrollment enrollment = new PayEnrollment(Status.RECRUIT, students, payPrice);
        enrollment.enroll(NsUserTest.JAVAJIGI, payment1);

        Assertions.assertThat(students.countOfStudent()).isEqualTo(1);
    }

    @Test
    @DisplayName("현재 모집중인 강의가 아니라면 수강신청이 불가능하다")
    void enroll_실패케이스_현재_모집중인_강의가_아닌_경우() {
        Enrollment enrollment = new PayEnrollment(Status.PREPARE, students, payPrice);

        Assertions.assertThatThrownBy(() -> {
            enrollment.enroll(NsUserTest.JAVAJIGI, payment1);
        }).isInstanceOf(CannotApplyException.class);
    }

    @Test
    @DisplayName("이미 등록된 학생인 경우 수강신청이 불가능하다")
    void enroll_실패케이스_이미_등록된_학생인_경우() {
        Enrollment enrollment = new PayEnrollment(Status.RECRUIT, students, payPrice);
        enrollment.enroll(NsUserTest.JAVAJIGI, payment1);

        Assertions.assertThatThrownBy(() -> {
            enrollment.enroll(NsUserTest.JAVAJIGI, payment2);
        }).isInstanceOf(CannotApplyException.class);
    }

    @Test
    @DisplayName("강의 정원이 초과된 경우 수강신청이 불가능하다")
    void enroll_실패케이스_정원이_초과된_경우() {
        Enrollment enrollment = new PayEnrollment(Status.RECRUIT, students, payPrice);

        enrollment.enroll(NsUserTest.JAVAJIGI, payment1);
        enrollment.enroll(NsUserTest.SANJIGI, payment2);

        NsUser newUser = new NsUser(3L, "mina", "password", "name", "mina@test.com");
        Payment payment3 = new Payment("3", 3L, 3L, 800_000L);

        Assertions.assertThatThrownBy(() -> {
            enrollment.enroll(newUser, payment3);
        }).isInstanceOf(CannotApplyException.class);
    }

    @Test
    @DisplayName("결제 금액과 수강료가 일치하지 않는 경우 수강신청이 불가능하다")
    void enroll_결제_금액과_수강료가_일치하지_않는_경우() {
        Enrollment enrollment = new PayEnrollment(Status.RECRUIT, students, payPrice);

        Payment payment = new Payment("1", 1L, 1L, 500_000L);

        Assertions.assertThatThrownBy(() -> {
            enrollment.enroll(NsUserTest.JAVAJIGI, payment);
        }).isInstanceOf(CannotApplyException.class);
    }
}