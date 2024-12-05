package nextstep.courses.domain;

import java.util.List;
import java.util.Set;
import nextstep.users.domain.NsUser;

public class Session {
    private Long id;
    private Charge charge;
    private EnrollmentsInfo enrollmentsInfo;
    private CoverImages coverImages;
    private SessionPeriod sessionPeriod;

    public Session(long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, List.of(), sessionPeriod);
    }

    public Session(Long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   List<CoverImage> coverImages,
                   SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, new CoverImages(coverImages), sessionPeriod);
    }

    public Session(Long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   CoverImages coverImages,
                   SessionPeriod sessionPeriod) {
        this(id, charge, new EnrollmentsInfo(enrollmentsFactory, sessionStatus), coverImages,
                sessionPeriod);
    }

    public Session(Long id, Charge charge, EnrollmentsInfo enrollmentsInfo, CoverImages coverImages,
                   SessionPeriod sessionPeriod) {
        this.id = id;
        this.charge = charge;
        this.enrollmentsInfo = enrollmentsInfo;
        this.coverImages = coverImages;
        this.sessionPeriod = sessionPeriod;
    }

    public Enrollments enrollments(int fee, Set<EnrollmentStudent> enrollmentStudents, NsUser user) {
        Enrollments enrollments = enrollments(enrollmentStudents);
        enrollments.enrollment(fee, this, user);
        return enrollments;
    }

    public Enrollments enrollments(Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsInfo.enrollments(this.charge, enrollmentStudents);
    }


    public long id() {
        return id;
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
