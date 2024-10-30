package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FreeSession extends Session {

    public FreeSession(SessionDate date, SessionImage image, SessionStatus status, List<Long> numOfStudents) {
        this(1L, date, image, status, numOfStudents);
    }

    public FreeSession(long id, Timestamp sessionStartAt, Timestamp sessionEndAt, SessionImage sessionImage, String status) {
        this(id, new SessionDate(sessionStartAt, sessionEndAt), sessionImage, SessionStatus.valueOf(status), new ArrayList<>());
    }

    public FreeSession(Long sessionId, SessionDate date, SessionImage image, SessionStatus status, List<Long> numOfStudents) {
        super(sessionId, date, image, status, numOfStudents);
    }

    @Override
    public void enroll(Payment payment) {
        if (!payment.isFree()) {
            throw new IllegalStateException("무료강의의 수강료는 지불되어선 안됩니다.");
        }
        super.enroll(payment);
    }

    @Override
    public String toString() {
        return "FreeSession{" +
                "sessionId=" + sessionId +
                ", date=" + date +
                ", image=" + image +
                ", status=" + status +
                ", students=" + students +
                '}';
    }
}
