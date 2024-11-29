package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

import java.util.Set;
import nextstep.courses.MaxEnrollmentExceededException;
import nextstep.users.domain.NsUser;

public class LimitedEnrollments extends Enrollments {
    private final Capacity capacity;

    public LimitedEnrollments(int capacity, SessionStatus sessionStatus) {
        this(new Capacity(capacity), sessionStatus);
    }

    public LimitedEnrollments(int capacity, SessionStatus sessionStatus, Set<NsUser> enrolledStudents) {
        this(new Capacity(capacity), sessionStatus, enrolledStudents);
    }

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus) {
        this(capacity, sessionStatus, Set.of());
    }

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus, Set<NsUser> enrolledStudents) {
        super(sessionStatus, enrolledStudents);
        validateNull(capacity);
        this.capacity = capacity;
    }

    @Override
    public void enrollment(NsUser student) {
        validateMaxEnrollmentExceeded();
        super.enrollment(student);
    }

    private void validateMaxEnrollmentExceeded() {
        if (!capacity.canEnroll(size())) {
            throw new MaxEnrollmentExceededException();
        }
    }
}
