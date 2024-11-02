package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.sql.Timestamp;
import java.util.List;

public class FreeSession extends Session {

    public FreeSession(long id, Timestamp sessionStartAt, Timestamp sessionEndAt, SessionImage sessionImage, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> numOfStudents) {
        this(id, new SessionDate(sessionStartAt, sessionEndAt), sessionImage, recruitingStatus, progressStatus, numOfStudents);
    }

    public FreeSession(long id, Timestamp sessionStartAt, Timestamp sessionEndAt, List<SessionImage> sessionImages, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> approvedStudents, List<Long> applyStudents) {
        this(id, new SessionDate(sessionStartAt, sessionEndAt), sessionImages, recruitingStatus, progressStatus, approvedStudents, applyStudents);
    }

    public FreeSession(Long sessionId, SessionDate date, SessionImage image, RecruitingStatus status, List<Long> numOfStudents) {
        super(sessionId, date, image, status, numOfStudents);
    }

    public FreeSession(Long sessionId, SessionDate date, SessionImage image, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> numOfStudents) {
        super(sessionId, date, image, recruitingStatus, progressStatus, numOfStudents);
    }

    public FreeSession(Long sessionId, SessionDate date, List<SessionImage> images, RecruitingStatus recruitingStatus, ProgressStatus progressStatus, List<Long> numOfStudents, List<Long> applyStudents) {
        super(sessionId, date, images, recruitingStatus, progressStatus, numOfStudents, applyStudents);
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
                ", recruitingStatus=" + recruitingStatus +
                ", progressStatus=" + progressStatus +
                ", students=" + students +
                '}';
    }
}
