package nextstep.courses.domain.session;

import nextstep.courses.exception.CannotIncreaseException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnrollmentTest {

    @Test
    void create() {
        Enrollment expected = new Enrollment(10);

        assertThat(expected).isEqualTo(new Enrollment(10));
    }

    @Test
    void 수강신청_성공() {
        Enrollment enrollment = new Enrollment(10);

        enrollment.enroll(NsUserTest.JAVAJIGI);

        assertThat(enrollment).isEqualTo(new Enrollment(List.of(NsUserTest.JAVAJIGI), 10));
    }

    @Test
    void 수강신청_초과() {
        Enrollment enrollment = new Enrollment(0);

        assertThatThrownBy(() -> {
            enrollment.enroll(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotIncreaseException.class);
    }
}
