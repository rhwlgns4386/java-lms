package nextstep.courses.domain.session;

import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.payments.domain.Payment;
import nextstep.qna.SessionException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session {

    private Long id;
    private SessionPayType payType;
    private SessionPays pays;
    private SessionStatus status;
    private SessionCoverImage coverImage;
    private SessionPeriod period;
    private SessionStudents students;

    public Session(SessionCoverImage image, SessionPayType payType, LocalDateTime startDate, LocalDateTime endDate, Long sessionPay, int maximumNumberPeople) {
        this.status = SessionStatus.READY;
        this.payType = payType;
        this.coverImage = image;
        this.pays = new SessionPays(sessionPay);
        this.period = new SessionPeriod(startDate, endDate);
        this.students = new SessionStudents(maximumNumberPeople);
    }

    public static Session createFree(SessionCoverImage image, LocalDateTime startDate, LocalDateTime endDate) {
        return new Session(image, SessionPayType.FREE, startDate, endDate, 0L, 0);
    }

    public static Session createPaid(SessionCoverImage image, LocalDateTime startDate, LocalDateTime endDate, Long sessionPay, int maximumNumberPeople) {
        return new Session(image, SessionPayType.PAID, startDate, endDate, sessionPay, maximumNumberPeople);
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
        pays.addPayments(payment);
    }

    private void validate() {
        status.validateRegistration();

        if (this.payType == SessionPayType.PAID && students.isExceeds()) {
            throw new SessionException("최대 정원 모집이 끝났습니다");
        }
    }

}
