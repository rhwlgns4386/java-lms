package nextstep.courses.domain.enrollment;

import nextstep.courses.type.EnrollmentState;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class StudentTest {

    Student student;

    @BeforeEach
    void init () {
        student = new Student(NsUserTest.JAVAJIGI, EnrollmentState.APPLY);
    }

    @Test
    void create() {
        assertThat(student).isNotNull();
    }

    @Test
    void approve() {
        student.approve();

        assertThat(student.isRegistered()).isTrue();
    }

    @Test
    void reject() {
        student.reject();

        assertThat(student.isRegistered()).isFalse();
    }

    @Test
    void throw_exception_if_try_reject_if_state_is_not_apply() {
        Student approvedStudent = new Student(NsUserTest.JAVAJIGI, EnrollmentState.APPROVE);
        assertThatIllegalStateException().isThrownBy(approvedStudent::approve);
    }

    @Test
    void throw_exception_if_try_approve_if_state_is_not_apply() {
        Student approvedStudent = new Student(NsUserTest.JAVAJIGI, EnrollmentState.APPROVE);
        assertThatIllegalStateException().isThrownBy(approvedStudent::reject);
    }
}
