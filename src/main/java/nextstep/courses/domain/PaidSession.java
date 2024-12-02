package nextstep.courses.domain;

import java.util.Set;

public class PaidSession extends Session {
    private Capacity capacity;

    public PaidSession(long id, Charge charge, Capacity capacity, SessionStatus sessionStatus, CoverImage test,
                       SessionPeriod sessionPeriod) {
        super(id, charge, sessionStatus, test, sessionPeriod);
        this.capacity = capacity;
    }

    @Override
    protected Enrollments enrollments(SessionStatus sessionStatus, Set<EnrollmentStudent> enrollmentStudents) {
        return new LimitedEnrollments(capacity, sessionStatus, enrollmentStudents);
    }
}
