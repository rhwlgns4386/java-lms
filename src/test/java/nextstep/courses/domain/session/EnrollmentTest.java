package nextstep.courses.domain.session;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class EnrollmentTest {

    @Test
    void throw_exception_if_exceed_max_enrollment() {
        Enrollment enrollment = new Enrollment(5);
        IntStream.range(0, 5).forEach(i -> enrollment.register());

        assertThatIllegalStateException().isThrownBy(enrollment::register);
    }

    @Test
    void infinite_enrollment() {
        Enrollment enrollment = new Enrollment(Enrollment.INFINITE_ENROLLMENT);
        IntStream.range(0, 100).forEach(i -> enrollment.register());

        assertThat(enrollment.isNotAvailable()).isFalse();
    }
}
