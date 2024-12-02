package nextstep.courses.domain;

import java.util.Set;
import nextstep.users.domain.NsUser;

public class Session {
    private EnrollmentsFactory enrollmentsFactory;
    private Long id;
    private Charge charge;
    private SessionStatus sessionStatus;
    private DefaultEnrollments enrollments;
    private CoverImage coverImage;
    private SessionPeriod sessionPeriod;

    public Session(Charge charge, DefaultEnrollments enrollments, CoverImage coverImage, SessionPeriod sessionPeriod) {
        this(null, charge, enrollments, coverImage, sessionPeriod);
    }

    public Session(Long id, Charge charge, DefaultEnrollments enrollments, CoverImage coverImage,
                   SessionPeriod sessionPeriod) {
        this(id, charge, null, null, enrollments, coverImage, sessionPeriod);
    }

    public Session(long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   CoverImage coverImage,
                   SessionPeriod sessionPeriod) {
        this(id, charge, sessionStatus, enrollmentsFactory, null, coverImage, sessionPeriod);
    }

    public Session(Long id, Charge charge, SessionStatus sessionStatus, EnrollmentsFactory enrollmentsFactory,
                   DefaultEnrollments enrollments, CoverImage coverImage,
                   SessionPeriod sessionPeriod) {
        this.id = id;
        this.charge = charge;
        this.sessionStatus = sessionStatus;
        this.enrollmentsFactory = enrollmentsFactory;
        this.enrollments = enrollments;
        this.coverImage = coverImage;
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

    public CoverImage coverImage() {
        return coverImage;
    }

    public SessionPeriod sessionPeriod() {
        return sessionPeriod;
    }
}
