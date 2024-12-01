package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class Session {
    private Long id;
    private Charge charge;
    private final Enrollments enrollments;
    private CoverImage coverImage;
    private SessionPeriod sessionPeriod;

    public Session(Charge charge, Enrollments enrollments, CoverImage coverImage, SessionPeriod sessionPeriod) {
        this(null, charge, enrollments, coverImage, sessionPeriod);
    }

    public Session(Long id, Charge charge, Enrollments enrollments, CoverImage coverImage,
                   SessionPeriod sessionPeriod) {
        this.id = id;
        this.charge = charge;
        this.enrollments = enrollments;
        this.coverImage = coverImage;
        this.sessionPeriod = sessionPeriod;
    }

    public void enrollment(Charge fee, NsUser enrollmentStudent) {
        validateCharge(fee);
        enrollments.enrollment(this, enrollmentStudent);
    }

    private void validateCharge(Charge fee) {
        if (!this.charge.equals(fee)) {
            throw new IllegalArgumentException("강의 금액과 일치하지 않습니다");
        }
    }

    public long getId() {
        return id;
    }

    public Enrollments getEnrollments() {
        return enrollments;
    }
}
