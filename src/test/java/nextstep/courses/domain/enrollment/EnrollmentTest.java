package nextstep.courses.domain.enrollment;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class EnrollmentTest {

    @Test
    void throw_exception_if_apply_duplicate_student() {
        Enrollment enrollment = new Enrollment(Enrollment.INFINITE_ENROLLMENT);
        enrollment.apply(getNsUser(1L, String.valueOf(1)));

        assertThatIllegalArgumentException().isThrownBy(() ->
                enrollment.apply(getNsUser(1L, String.valueOf(1))));
    }

    private NsUser getNsUser(Long id, String userId) {
        return new NsUser(id, userId, "1", id + "user", id + "@dummy.co.kr");
    }

    @Test
    void throw_exception_if_students_count_exceed_max_enrollment() {
        Enrollment enrollment = new Enrollment(5);
        registerStudent(5, enrollment);

        assertThat(enrollment.getEnrollment()).isEqualTo(5);
        assertThatIllegalStateException()
                .isThrownBy(() -> enrollment.apply(getNsUser(10L, "10")));
    }

    private void registerStudent(int endExclusive, Enrollment enrollment) {
        List<NsUser> students = LongStream.range(0, endExclusive)
                .mapToObj(i -> getNsUser(i, String.valueOf(i)))
                .collect(Collectors.toList());

        students.forEach(enrollment::apply);
        students.forEach(enrollment::register);
    }

    @Test
    void students_infinite_enrollment() {
        Enrollment enrollment = new Enrollment(Enrollment.INFINITE_ENROLLMENT);
        registerStudent(100, enrollment);

        assertThat(enrollment.isNotAvailable()).isFalse();
    }
}
