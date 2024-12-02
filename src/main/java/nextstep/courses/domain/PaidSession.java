package nextstep.courses.domain;

import java.util.Set;

public class PaidSession extends Session {
    private Capacity capacity;

    public PaidSession(long id, Charge charge, Capacity capacity, SessionStatus sessionStatus,
                       EnrollmentsFactory enrollmentsFactory, CoverImage coverImage,
                       SessionPeriod sessionPeriod) {
        super(id, charge, sessionStatus, enrollmentsFactory, coverImage, sessionPeriod);
        this.capacity = capacity;
    }

    @Override
    protected DefaultEnrollments enrollments(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus,
                                             Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsFactory.enrollments(capacity, sessionStatus, enrollmentStudents);
    }
}
