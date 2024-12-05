package nextstep.courses.domain;

import java.util.List;
import java.util.Set;
import nextstep.users.domain.NsUser;

public class Session {
    private SessionId id;
    private SessionInfo sessionInfo;

    public Session(SessionId id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, List.of(), sessionPeriod);
    }

    public Session(SessionId id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   List<CoverImage> coverImages, SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, new CoverImages(coverImages), sessionPeriod);
    }

    public Session(SessionId id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   CoverImages coverImages, SessionPeriod sessionPeriod) {
        this(id, charge, new EnrollmentsInfo(enrollmentsFactory, sessionStatus), coverImages, sessionPeriod);
    }

    public Session(SessionId id, Charge charge, EnrollmentsInfo enrollmentsInfo, CoverImages coverImages,
                   SessionPeriod sessionPeriod) {
        this.id = id;
        this.sessionInfo = new SessionInfo(charge, enrollmentsInfo, coverImages, sessionPeriod);
    }

    public Enrollments enrollments(int fee, Set<EnrollmentStudent> enrollmentStudents, NsUser user) {
        return sessionInfo.enrollments(fee, this, enrollmentStudents, user);
    }

    public Enrollments enrollments(Set<EnrollmentStudent> enrollmentStudents) {
        return sessionInfo.enrollments(enrollmentStudents);
    }


    public Long id() {
        if (id == null) {
            return null;
        }
        return id.toLonge();
    }

    public Charge charge() {
        return sessionInfo.charge();
    }

    public SessionPeriod sessionPeriod() {
        return sessionInfo.sessionPeriod();
    }

    public EnrollmentsInfo enrollmentsInfo() {
        return sessionInfo.enrollmentsInfo();
    }

}
