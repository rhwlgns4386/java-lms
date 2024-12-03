package nextstep.courses.infrastructure;

import java.util.Set;
import nextstep.courses.domain.Capacity;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.DefaultEnrollments;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.EnrollmentsFactory;
import nextstep.courses.domain.SessionStatus;

public class ProxyEnrollmentsFactory extends EnrollmentsFactory {

    @Override
    protected Enrollments enrollments(SessionStatus sessionStatus, Set<EnrollmentStudent> enrollmentStudents) {
        return new EnrollmentsInsertCacheProxy(super.enrollments(sessionStatus, enrollmentStudents));
    }

    @Override
    public Enrollments enrollments(Capacity capacity, SessionStatus sessionStatus,
                                   Set<EnrollmentStudent> enrollmentStudents) {
        return new EnrollmentsInsertCacheProxy(super.enrollments(capacity, sessionStatus, enrollmentStudents));
    }
}
