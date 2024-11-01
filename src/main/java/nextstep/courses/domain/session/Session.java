package nextstep.courses.domain.session;

import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.payments.domain.Payment;
import nextstep.courses.SessionException;
import nextstep.users.domain.NsUser;

public class Session {

    private Long id;
    private Long courseId;
    private SessionPeriod period;
    private SessionPay sessionPay;
    private SessionStatus status;
    private SessionCoverImage coverImage;
    private SessionStudents students;

    public Session(Long courseId, SessionCoverImage image, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        this.status = SessionStatus.READY;
        this.coverImage = image;
        this.sessionPay = sessionPay;
        this.period = sessionPeriod;
        this.students = new SessionStudents(maximumNumberPeople);
    }

    public static Session createFree(Long courseId, SessionCoverImage image, SessionPay sessionPay, SessionPeriod sessionPeriod) {
        return new Session(courseId, image, sessionPay, sessionPeriod, 0);
    }

    public static Session createPaid(Long courseId, SessionCoverImage image, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        return new Session(courseId, image, sessionPay, sessionPeriod, maximumNumberPeople);
    }

    public void recruiting() {
        this.status = SessionStatus.RECRUITING;
    }

    public void finish() {
        this.status = SessionStatus.FINISH;
    }

    public void registration(NsUser nsUser, Payment payment) {
        validate();
        students.registration(id, nsUser);
        sessionPay.validatePay(payment);
    }

    private void validate() {
        status.validateRegistration();
        if (sessionPay.isPaid() && students.isExceeds()) {
            throw new SessionException("최대 정원 모집이 끝났습니다");
        }
    }

}
