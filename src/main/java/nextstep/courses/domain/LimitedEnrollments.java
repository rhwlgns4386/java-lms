package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

import java.util.Set;
import nextstep.courses.MaxEnrollmentExceededException;
import nextstep.users.domain.NsUser;

public class LimitedEnrollments extends DefaultEnrollments {
    private final Capacity capacity;

    public LimitedEnrollments(int capacity, SessionStatus sessionStatus) {
        this(new Capacity(capacity), sessionStatus, Set.of());
    }

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus) {
        this(capacity, sessionStatus, Set.of());
    }

    public LimitedEnrollments(int capacity, SessionStatus sessionStatus, Set<EnrollmentStudent> enrolledStudents) {
        this(new Capacity(capacity), sessionStatus, enrolledStudents);
    }

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus, Set<EnrollmentStudent> enrolledStudents) {
        super(sessionStatus, enrolledStudents);
        validateNull(capacity);
        this.capacity = capacity;
    }

    @Override
    public void enrollment(Session session, NsUser student) {
        super.enrollment(session, student);
    }

    @Override
    public void enrollment(EnrollmentStudent student) {
        validateMaxEnrollmentExceeded();
        super.enrollment(student);
    }

    private void validateMaxEnrollmentExceeded() {
        if (!capacity.canEnroll(size())) {
            throw new MaxEnrollmentExceededException();
        }
    }
}
