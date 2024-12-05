package nextstep.courses.domain;

import java.util.Set;

public class EnrollmentsFactory {
    public EnrollmentsFactory() {
    }


    public Enrollments enrollments(Charge charge, SessionStatus sessionStatus,
                                   Set<EnrollmentStudent> enrollmentStudents) {
        return new Enrollments(charge, sessionStatus, enrollmentStudents);
    }

    public Enrollments enrollments(Capacity capacity, Charge charge, SessionStatus sessionStatus,
                                   Set<EnrollmentStudent> enrollmentStudents) {
        if (capacity == null) {
            enrollments(charge, sessionStatus, enrollmentStudents);
        }
        return new LimitedEnrollments(capacity, charge, sessionStatus, enrollmentStudents);
    }
}
