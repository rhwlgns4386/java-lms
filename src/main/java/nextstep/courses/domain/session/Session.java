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
    private SessionStudents students;

    public Session(Long courseId, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        this.status = SessionStatus.READY;

        this.sessionPay = sessionPay;
        this.period = sessionPeriod;
        this.students = new SessionStudents(maximumNumberPeople);
    }

    public static Session createFree(Long courseId, SessionPay sessionPay, SessionPeriod sessionPeriod) {
        return new Session(courseId, sessionPay, sessionPeriod, 0);
    }

    public static Session createPaid(Long courseId, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        return new Session(courseId, sessionPay, sessionPeriod, maximumNumberPeople);
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

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public SessionPeriod getPeriod() {
        return period;
    }

    public SessionPay getSessionPay() {
        return sessionPay;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public SessionStudents getStudents() {
        return students;
    }
}
