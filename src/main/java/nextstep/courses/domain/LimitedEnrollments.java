package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

import java.util.Set;
import nextstep.courses.MaxEnrollmentExceededException;
import nextstep.users.domain.NsUser;

public class LimitedEnrollments extends Enrollments {
    private final Capacity capacity;

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus) {
        this(capacity, sessionStatus, Set.of());
    }

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus, Set<EnrollmentStudent> enrolledStudents) {
        super(sessionStatus, enrolledStudents);
        validateNull(capacity);
        this.capacity = capacity;
    }

    public LimitedEnrollments(int capacity, SessionStatus sessionStatus, Session session) {
        this(new Capacity(capacity), sessionStatus, session);
    }

    public LimitedEnrollments(int capacity, SessionStatus sessionStatus, Session session,
                              Set<NsUser> enrolledStudents) {
        this(new Capacity(capacity), sessionStatus, session, enrolledStudents);
    }

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus, Session session) {
        this(capacity, sessionStatus, session, Set.of());
    }

    public LimitedEnrollments(Capacity capacity, SessionStatus sessionStatus, Session session,
                              Set<NsUser> enrolledStudents) {
        super(sessionStatus, session, enrolledStudents);
        validateNull(capacity);
        this.capacity = capacity;
    }

    @Override
    public void enrollment(Session session, NsUser student) {
        validateMaxEnrollmentExceeded2();
        super.enrollment(session, student);
    }

    private void validateMaxEnrollmentExceeded2() {
        if (!capacity.canEnroll(size())) {
            throw new MaxEnrollmentExceededException();
        }
    }
}
