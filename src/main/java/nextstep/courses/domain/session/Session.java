package nextstep.courses.domain.session;

import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.payments.domain.Payment;
import nextstep.qna.SessionException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session {

    private Long id;
    private SessionPeriod period;
    private SessionPay sessionPay;
    private SessionStatus status;
    private SessionCoverImage coverImage;
    private SessionStudents students;

    public Session(SessionCoverImage image, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        this.status = SessionStatus.READY;
        this.coverImage = image;
        this.sessionPay = sessionPay;
        this.period = sessionPeriod;
        this.students = new SessionStudents(maximumNumberPeople);
    }

    public static Session createFree(SessionCoverImage image, SessionPay sessionPay, SessionPeriod sessionPeriod) {
        return new Session(image, sessionPay, sessionPeriod, 0);
    }

    public static Session createPaid(SessionCoverImage image, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        return new Session(image, sessionPay, sessionPeriod, maximumNumberPeople);
    }

    public void recruiting() {
        this.status = SessionStatus.RECRUITING;
    }

    public void finish() {
        this.status = SessionStatus.FINISH;
    }

    public void registration(NsUser nsUser, Payment payment) {
        validate();
        students.registration(nsUser);
        sessionPay.validatePay(payment);
    }

    private void validate() {
        status.validateRegistration();
        if (sessionPay.isPaid() && students.isExceeds()) {
            throw new SessionException("최대 정원 모집이 끝났습니다");
        }
    }

}
