package nextstep.courses.domain;

import java.util.Set;
import nextstep.users.domain.NsUser;

public class EnrollmentsInfo {
    private EnrollmentsFactory enrollmentsFactory;
    private SessionStatus sessionStatus;
    private DefaultEnrollments enrollments;

    public EnrollmentsInfo(DefaultEnrollments enrollments) {
        this(null, null, enrollments);
    }

    public EnrollmentsInfo(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus) {
        this(enrollmentsFactory, sessionStatus, null);
    }

    public EnrollmentsInfo(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus,
                           DefaultEnrollments enrollments) {
        this.enrollmentsFactory = enrollmentsFactory;
        this.sessionStatus = sessionStatus;
        this.enrollments = enrollments;
    }

    void enrollment(Session session, NsUser enrollmentStudent) {
        enrollments.enrollment(session, enrollmentStudent);
    }

    Enrollments enrollments(Set<EnrollmentStudent> enrollmentStudents) {
        return enrollments(this.enrollmentsFactory, sessionStatus, enrollmentStudents);
    }

    protected Enrollments enrollments(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus,
                                      Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsFactory.enrollments(sessionStatus, enrollmentStudents);
    }

    public DefaultEnrollments enrollments() {
        return enrollments;
    }

    public Set<EnrollmentStudent> enrollmentStudents() {
        return enrollments.enrolledStudents();
    }
}
