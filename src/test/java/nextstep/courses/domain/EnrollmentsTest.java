package nextstep.courses.domain;

import static nextstep.courses.domain.TestEnrollmentsFactory.enrollments;
import static nextstep.courses.domain.TestSessionFactory.createTestSession;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.courses.NonReadyException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EnrollmentsTest {

    @Test
    void 등록목록에_등록한다() {
        Session session = createTestSession();
        DefaultEnrollments enrollments = enrollments(SessionStatus.PROGRESS, session,
                Set.of(NsUserTest.JAVAJIGI));
        enrollments.enrollment(session, NsUserTest.SANJIGI);

        assertThat(enrollments).isEqualTo(
                enrollments(SessionStatus.PROGRESS, session, Set.of(NsUserTest.JAVAJIGI, NsUserTest.SANJIGI)));
    }

    @Test
    void 이미추가된_사용자면_예외가_발생한다() {
        Session session = createTestSession();
        DefaultEnrollments enrollments = enrollments(SessionStatus.PROGRESS, session, Set.of(NsUserTest.JAVAJIGI));
        assertThatThrownBy(() -> enrollments.enrollment(session, NsUserTest.JAVAJIGI)).isInstanceOf(
                DuplicateStudentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"PREPARING", "CLOSED"})
    void 진행상태가_아니라면_예외(SessionStatus sessionStatus) {
        Session session = createTestSession();
        DefaultEnrollments enrollments = enrollments(sessionStatus, session);
        assertThatThrownBy(() -> enrollments.enrollment(session, NsUserTest.JAVAJIGI)).isInstanceOf(
                NonReadyException.class);
    }

}
