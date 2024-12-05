package nextstep.courses.domain;

import static nextstep.courses.domain.TestEnrollmentsFactory.enrollments;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashSet;
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
        Session session = TestSessionFactory.createTestSession();
        Enrollments enrollments = enrollments(SessionStatus.PROGRESS, session,
                Set.of(NsUserTest.JAVAJIGI));

        enrollments.enrollment(0, session, NsUserTest.SANJIGI);

        assertThat(enrollments).isEqualTo(
                enrollments(SessionStatus.PROGRESS, session, Set.of(NsUserTest.JAVAJIGI, NsUserTest.SANJIGI)));
    }

    @Test
    void 이미추가된_사용자면_예외가_발생한다() {
        Session session = TestSessionFactory.createTestSession();
        Enrollments enrollments = enrollments(SessionStatus.PROGRESS, session, Set.of(NsUserTest.JAVAJIGI));

        assertThatThrownBy(() -> enrollments.enrollment(0, session, NsUserTest.JAVAJIGI)).isInstanceOf(
                DuplicateStudentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"PREPARING", "CLOSED"})
    void 진행상태가_아니라면_예외(SessionStatus sessionStatus) {
        Session session = TestSessionFactory.createTestSession();
        Enrollments enrollments = enrollments(sessionStatus, session);

        assertThatThrownBy(() -> enrollments.enrollment(0, session, NsUserTest.JAVAJIGI)).isInstanceOf(
                NonReadyException.class);
    }

    @Test
    void 신청자를_등록한다() {
        Session session = TestSessionFactory.createTestSession();
        Enrollments enrollments = enrollments(SessionStatus.PROGRESS, session,
                Set.of(NsUserTest.JAVAJIGI, NsUserTest.SANJIGI));

        enrollments.accept(NsUserTest.JAVAJIGI);

        Set<EnrollmentStudent> enrollmentStudents = new HashSet<>(
                Set.of(new EnrollmentStudent(session, NsUserTest.JAVAJIGI
                        , RequestStatus.ACCEPTED), new EnrollmentStudent(session, NsUserTest.SANJIGI)));
        Enrollments result = enrollments(SessionStatus.PROGRESS, enrollmentStudents);
        assertThat(enrollments).isEqualTo(result);
    }

    @Test
    void 구매_금액과_일치하지않으면_예외() {
        Session session = TestSessionFactory.createTestSession();
        Enrollments enrollments = enrollments(SessionStatus.PROGRESS, session,
                Set.of(NsUserTest.JAVAJIGI, NsUserTest.SANJIGI));

        assertThatIllegalArgumentException().isThrownBy(() -> enrollments.enrollment(1, session, NsUserTest.JAVAJIGI));
    }

}
