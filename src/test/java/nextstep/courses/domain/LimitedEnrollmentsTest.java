package nextstep.courses.domain;

import static nextstep.courses.domain.TestEnrollmentsFactory.limitEnrollments;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import nextstep.courses.MaxEnrollmentExceededException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class LimitedEnrollmentsTest {

    @Test
    void 수강최대인원이_다찬경우_예외를_발생시킨2() {
        Session session = TestSessionFactory.createTestSession();
        DefaultEnrollments enrollments = limitEnrollments(1, SessionStatus.ENROLLING, session,
                Set.of(NsUserTest.JAVAJIGI));
        assertThatThrownBy(() -> enrollments.enrollment(session, NsUserTest.SANJIGI)).isInstanceOf(
                MaxEnrollmentExceededException.class);
    }
}
