package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.sql.Timestamp;
import java.util.List;

public class PaidSession extends Session {
    private final int maxNumOfStudents;
    private final int sessionFee;

    public PaidSession(long id, Timestamp sessionStartAt, Timestamp sessionEndAt, SessionImage sessionImage, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> numOfStudents, int maxStudent, int sessionFee) {
        this(id, new SessionDate(sessionStartAt, sessionEndAt), sessionImage, recruitingStatus, progressStatus, numOfStudents, maxStudent, sessionFee);
    }

    public PaidSession(Long sessionId, SessionDate date, SessionImage image, RecruitingStatus status, List<Long> numOfStudents, int maxNumOfStudents, int sessionFee) {
        super(sessionId, date, image, status, numOfStudents);
        if (maxNumOfStudents < students.size()) {
            throw new IllegalArgumentException("수강 정원이 초과됐습니다.");
        }
        this.maxNumOfStudents = maxNumOfStudents;
        this.sessionFee = sessionFee;
    }

    public PaidSession(Long sessionId, SessionDate date, SessionImage image, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> numOfStudents, int maxNumOfStudents, int sessionFee) {
        super(sessionId, date, image, recruitingStatus, progressStatus, numOfStudents);
        if (maxNumOfStudents < students.size()) {
            throw new IllegalArgumentException("수강 정원이 초과됐습니다.");
        }
        this.maxNumOfStudents = maxNumOfStudents;
        this.sessionFee = sessionFee;
    }

    @Override
    public void enroll(Payment payment) {
        if (maxNumOfStudents <= students.size()) {
            throw new IllegalStateException("수강 정원이 초과됐습니다.");
        }
        if (!payment.isPaid(sessionFee)) {
            throw new IllegalStateException("지불한 수강료가 일치하지 않습니다.");
        }
        super.enroll(payment);
    }

    public int getMaxNumOfStudents() {
        return maxNumOfStudents;
    }

    public int getSessionFee() {
        return sessionFee;
    }

    @Override
    public String toString() {
        return "PaidSession{" +
                "maxNumOfStudents=" + maxNumOfStudents +
                ", sessionFee=" + sessionFee +
                ", sessionId=" + sessionId +
                ", date=" + date +
                ", image=" + image +
                ", recruitingStatus=" + recruitingStatus +
                ", progressStatus=" + progressStatus +
                ", students=" + students +
                '}';
    }
}
