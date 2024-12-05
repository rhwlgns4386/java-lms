package nextstep.courses.domain;

import java.util.Set;

public class LimitEnrollmentsInfo extends EnrollmentsInfo {
    private Capacity capacity;

    public LimitEnrollmentsInfo(Capacity capacity, EnrollmentsFactory enrollmentsFactory,
                                SessionStatus sessionStatus) {
        super(enrollmentsFactory, sessionStatus);
        this.capacity = capacity;
    }

    @Override
    protected Enrollments enrollments(EnrollmentsFactory enrollmentsFactory, Charge charge, SessionStatus sessionStatus,
                                      Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsFactory.enrollments(capacity, charge, sessionStatus, enrollmentStudents);
    }

    public int capacity() {
        return capacity.toInt();
    }
}
