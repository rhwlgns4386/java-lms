package nextstep.courses.domain;

import java.util.Set;
import nextstep.users.domain.NsUser;

public class SessionInfo {
    private Charge charge;
    private EnrollmentsInfo enrollmentsInfo;
    private CoverImages coverImages;
    private SessionPeriod sessionPeriod;

    public SessionInfo(Charge charge, EnrollmentsInfo enrollmentsInfo, CoverImages coverImages,
                       SessionPeriod sessionPeriod) {
        this.charge = charge;
        this.enrollmentsInfo = enrollmentsInfo;
        this.coverImages = coverImages;
        this.sessionPeriod = sessionPeriod;
    }

    public Enrollments enrollments(int fee, Session session, Set<EnrollmentStudent> enrollmentStudents, NsUser user) {
        Enrollments enrollments = enrollments(enrollmentStudents);
        enrollments.enrollment(fee, session, user);
        return enrollments;
    }

    public Enrollments enrollments(Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsInfo.enrollments(this.charge, enrollmentStudents);
    }

    public Charge charge() {
        return charge;
    }

    public SessionPeriod sessionPeriod() {
        return sessionPeriod;
    }

    public EnrollmentsInfo enrollmentsInfo() {
        return enrollmentsInfo;
    }
}
