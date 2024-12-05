package nextstep.courses.domain;

import java.util.Set;

public class EnrollmentsInfo {
    private EnrollmentsFactory enrollmentsFactory;
    private SessionStatus sessionStatus;


    public EnrollmentsInfo(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus) {
        this.enrollmentsFactory = enrollmentsFactory;
        this.sessionStatus = sessionStatus;
    }


    public Enrollments enrollments(Charge charge, Set<EnrollmentStudent> enrollmentStudents) {
        return enrollments(enrollmentsFactory, charge, sessionStatus, enrollmentStudents);
    }

    protected Enrollments enrollments(EnrollmentsFactory enrollmentsFactory, Charge charge, SessionStatus sessionStatus,
                                      Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsFactory.enrollments(charge, sessionStatus, enrollmentStudents);
    }

    public SessionStatus sessionStatus() {
        return sessionStatus;
    }
}
