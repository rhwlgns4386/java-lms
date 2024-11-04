package nextstep.courses.domain.session;

import nextstep.courses.domain.session.enrollment.Enrollment;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {
    private final SessionInfo sessionInfo;

    private final Enrollment enrollment;

    private final SessionPeriod sessionPeriod;

    public Session(SessionInfo sessionInfo, Enrollment enrollment, SessionPeriod sessionPeriod) {
        this.sessionInfo = sessionInfo;
        this.enrollment = enrollment;
        this.sessionPeriod = sessionPeriod;
    }

    public void enroll(NsUser student, Payment payment) {
        enrollment.enroll(student, payment);
    }

}
