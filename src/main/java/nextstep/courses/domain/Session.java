package nextstep.courses.domain;

import java.util.List;
import java.util.Set;
import nextstep.users.domain.NsUser;

public class Session {
    private Long id;
    private Charge charge;
    private EnrollmentsFactory enrollmentsFactory;
    private SessionStatus sessionStatus;
    private DefaultEnrollments enrollments;
    private CoverImages coverImages;
    private SessionPeriod sessionPeriod;

    public Session(Charge charge, DefaultEnrollments enrollments, SessionPeriod sessionPeriod) {
        this(null, charge, enrollments, null, sessionPeriod);
    }

    public Session(Long id, Charge charge, DefaultEnrollments enrollments, SessionPeriod sessionPeriod) {
        this(id, charge, enrollments, List.of(), sessionPeriod);
    }

    public Session(Long id, Charge charge, DefaultEnrollments enrollments, List<CoverImage> coverImages,
                   SessionPeriod sessionPeriod) {
        this(id, charge, null, null, enrollments, coverImages, sessionPeriod);
    }

    public Session(long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, null, List.of(), sessionPeriod);
    }

    public Session(long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   List<CoverImage> coverImages, SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, null, coverImages, sessionPeriod);
    }

    public Session(Long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   DefaultEnrollments enrollments, List<CoverImage> coverImages,
                   SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, enrollments, new CoverImages(coverImages), sessionPeriod);
    }

    public Session(Long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   DefaultEnrollments enrollments, CoverImages coverImages,
                   SessionPeriod sessionPeriod) {
        this.id = id;
        this.charge = charge;
        this.sessionStatus = sessionStatus;
        this.enrollmentsFactory = enrollmentsFactory;
        this.enrollments = enrollments;
        this.coverImages = coverImages;
        this.sessionPeriod = sessionPeriod;
    }


    public void enrollment(int fee, NsUser enrollmentStudent) {
        enrollment(new Charge(fee), enrollmentStudent);
    }

    public void enrollment(Charge fee, NsUser enrollmentStudent) {
        validateCharge(fee);
        enrollments.enrollment(this, enrollmentStudent);
    }

    public Enrollments enrollments(int fee, Set<EnrollmentStudent> enrollmentStudents, NsUser user) {
        Enrollments enrollments = enrollments(fee, enrollmentStudents);
        enrollments.enrollment(this, user);
        return enrollments;
    }

    public Enrollments enrollments(int fee, Set<EnrollmentStudent> enrollmentStudents) {
        return enrollments(new Charge(fee), enrollmentStudents);
    }

    public Enrollments enrollments(Charge charge, Set<EnrollmentStudent> enrollmentStudents) {
        validateCharge(charge);
        return enrollments(enrollmentsFactory, sessionStatus, enrollmentStudents);
    }

    protected Enrollments enrollments(EnrollmentsFactory enrollmentsFactory, SessionStatus sessionStatus,
                                      Set<EnrollmentStudent> enrollmentStudents) {
        return enrollmentsFactory.enrollments(sessionStatus, enrollmentStudents);
    }

    private void validateCharge(Charge fee) {
        if (!this.charge.equals(fee)) {
            throw new IllegalArgumentException("강의 금액과 일치하지 않습니다");
        }
    }

    public long id() {
        return id;
    }

    public DefaultEnrollments enrollments() {
        return enrollments;
    }

    public Set<EnrollmentStudent> enrollmentStudents() {
        return enrollments.enrolledStudents();
    }

    public Charge charge() {
        return charge;
    }

    public SessionPeriod sessionPeriod() {
        return sessionPeriod;
    }
}
