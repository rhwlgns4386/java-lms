package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Set;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class SessionTest {

    @Test
    void 수강신청시_강의금액과_같지않으면_예외() {
        Session session = TestSessionFactory.createTestSession(500);
        assertThatIllegalArgumentException().isThrownBy(() -> session.enrollment(499, NsUserTest.JAVAJIGI));
    }

    @Test
    void 수강신청이_가능하면_등록자들을_반환한다() {
        Session session = TestSessionFactory.createTestSession2();
        Set<EnrollmentStudent> enrollmentStudents = Set.of(new EnrollmentStudent(0L, 0L));

        Enrollments enrollments = session.enrollments(0, enrollmentStudents);

        assertThat(enrollments).isEqualTo(new Enrollments(SessionStatus.ENROLLING, enrollmentStudents));
    }

    @Test
    void 수강신청이_가능하면_최대개수가_제한된_등록자들을_반환한다() {
        Session session = TestSessionFactory.createTestSession2(500, 2);
        Set<EnrollmentStudent> enrollmentStudents = Set.of(new EnrollmentStudent(0L, 0L));

        Enrollments enrollments = session.enrollments(500, enrollmentStudents);

        assertThat(enrollments).isEqualTo(new LimitedEnrollments(2, SessionStatus.ENROLLING, enrollmentStudents));
    }

    @Test
    void 사용자를_추가로_입력시_해당사용자가_추가된_등록자들을_반환한다() {
        Session session = TestSessionFactory.createTestSession2(500, 2);
        Set<EnrollmentStudent> enrollmentStudents = Set.of(new EnrollmentStudent(0L, 0L));

        Enrollments enrollments = session.enrollments(500, enrollmentStudents, NsUserTest.JAVAJIGI);

        assertThat(enrollments).isEqualTo(new LimitedEnrollments(2, SessionStatus.ENROLLING,
                Set.of(new EnrollmentStudent(0L, 0L), new EnrollmentStudent(0L, 1L))));
    }

}
