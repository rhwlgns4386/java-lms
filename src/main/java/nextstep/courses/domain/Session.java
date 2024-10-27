package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public abstract class Session {

    protected final Long sessionId;
    protected final SessionDate date;
    protected final SessionImage image;
    protected final SessionStatus status;
    protected List<Long> students;

    public Session(Long sessionId, SessionDate date, SessionImage image, SessionStatus status, List<Long> numOfStudents) {
        this.sessionId = sessionId;
        this.date = date;
        this.image = image;
        this.status = status;
        this.students = numOfStudents;
    }

    public void enroll(Payment payment) {
        if (!status.canEnroll()) {
            throw new IllegalStateException("강의가 모집 상태가 아닙니다.");
        }
        this.students.add(payment.getNsUserId());
    }

    public boolean isContainUser(NsUser user) {
        return students.contains(user.getId());
    }

    public Long getSessionId() {
        return sessionId;
    }
}
