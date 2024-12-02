package nextstep.courses.domain;

import java.util.Set;

public class EnrollmentsFactory {
    public EnrollmentsFactory() {
    }

    protected Enrollments enrollments(SessionStatus sessionStatus, Set<EnrollmentStudent> enrollmentStudents) {
        return new Enrollments(sessionStatus, enrollmentStudents);
    }

    public Enrollments enrollments(Capacity capacity, SessionStatus sessionStatus,
                                   Set<EnrollmentStudent> enrollmentStudents) {
        if (capacity == null) {
            enrollments(sessionStatus, enrollmentStudents);
        }
        return new LimitedEnrollments(capacity, sessionStatus, enrollmentStudents);
    }
}
