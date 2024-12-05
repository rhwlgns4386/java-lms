package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

import java.util.Set;
import nextstep.courses.MaxEnrollmentExceededException;

public class LimitedEnrollments extends Enrollments {
    private final Capacity capacity;


    public LimitedEnrollments(int capacity, Charge charge, SessionStatus sessionStatus,
                              Set<EnrollmentStudent> enrolledStudents) {
        this(new Capacity(capacity), charge, sessionStatus, enrolledStudents);
    }

    public LimitedEnrollments(Capacity capacity, Charge charge, SessionStatus sessionStatus,
                              Set<EnrollmentStudent> enrolledStudents) {
        super(charge, sessionStatus, enrolledStudents);
        validateNull(capacity);
        this.capacity = capacity;
    }

    @Override
    public void enrollment(Charge fee, EnrollmentStudent student) {
        validateMaxEnrollmentExceeded();
        super.enrollment(fee, student);
    }

    private void validateMaxEnrollmentExceeded() {
        if (!capacity.canEnroll(size())) {
            throw new MaxEnrollmentExceededException();
        }
    }

    public int capacity() {
        return capacity.toInt();
    }
}
