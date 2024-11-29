package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import nextstep.courses.MaxEnrollmentExceededException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class LimitedEnrollmentsTest {

    @Test
    void 수강최대인원이_다찬경우_예외를_발생시킨다() {
        Enrollments enrollments = new LimitedEnrollments(1, SessionStatus.ENROLLING, Set.of(NsUserTest.JAVAJIGI));
        assertThatThrownBy(() -> enrollments.enrollment(NsUserTest.SANJIGI)).isInstanceOf(
                MaxEnrollmentExceededException.class);
    }
}
