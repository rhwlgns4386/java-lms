package nextstep.courses.domain;

import java.util.List;
import java.util.Set;

public class PaidSession extends Session {
    private Capacity capacity;

    public PaidSession(long id, Charge charge, Capacity capacity, SessionStatus sessionStatus,
                       EnrollmentsFactory enrollmentsFactory, List<CoverImage> coverImages,
                       SessionPeriod sessionPeriod) {
        super(id, charge, sessionStatus, enrollmentsFactory, coverImages, sessionPeriod);
        this.capacity = capacity;
    }

    @Override
    protected Enrollments enrollments(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus,
                                      Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsFactory.enrollments(capacity, sessionStatus, enrollmentStudents);
    }
}
