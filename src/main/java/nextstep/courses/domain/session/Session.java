package nextstep.courses.domain.session;

import nextstep.courses.SessionException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class Session {
    private Long id;
    private Long courseId;
    private SessionPeriod period;
    private SessionPay sessionPay;
    private SessionStatus status;
    private SessionStudents students;

    public Session(Long id, Long courseId, String status, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        this(id, courseId, SessionStatus.search(status), sessionPay, sessionPeriod, maximumNumberPeople);
    }

    public Session(Long id, Long courseId, SessionStatus status, SessionPay sessionPay, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        this.id = id;
        this.status = status;
        this.courseId = courseId;
        this.sessionPay = sessionPay;
        this.period = sessionPeriod;
        this.students = new SessionStudents(maximumNumberPeople);
    }

    public static Session createFree(Long courseId, SessionPeriod sessionPeriod) {
        return new Session(0L, courseId, SessionStatus.READY, new SessionPay(0L, SessionPayType.PAID), sessionPeriod, 0);
    }

    public static Session createPaid(Long courseId, Long payAmount, SessionPeriod sessionPeriod, int maximumNumberPeople) {
        return new Session(0L, courseId, SessionStatus.READY, new SessionPay(payAmount, SessionPayType.PAID), sessionPeriod, maximumNumberPeople);
    }

    public void recruiting() {
        this.status = SessionStatus.RECRUITING;
    }

    public void finish() {
        this.status = SessionStatus.FINISH;
    }

    public SessionStudent registration(NsUser nsUser, Payment payment) {
        validate();
        sessionPay.validatePay(payment);
        return students.registration(id, nsUser);
    }

    public void mapping(List<SessionStudent> sessionStudents) {
        this.students.mapping(sessionStudents);
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
