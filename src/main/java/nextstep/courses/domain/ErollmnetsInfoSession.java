package nextstep.courses.domain;

import java.util.Set;

public class ErollmnetsInfoSession extends EnrollmentsInfo {
    private Capacity capacity;

    public ErollmnetsInfoSession(Capacity capacity, EnrollmentsFactory enrollmentsFactory,
                                 SessionStatus sessionStatus) {
        super(enrollmentsFactory, sessionStatus);
        this.capacity = capacity;
    }

    @Override
    protected Enrollments enrollments(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus,
                                      Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsFactory.enrollments(capacity, sessionStatus, enrollmentStudents);
    }
}
