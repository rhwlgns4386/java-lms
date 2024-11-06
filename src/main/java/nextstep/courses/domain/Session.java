package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Session {
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private SessionImage sessionImage;

    private boolean isFree;

    private SessionStatus sessionStatus;

    private MaxEnrollment maxEnrollment;

    private CurrentEnrollment currentEnrollment;

    public Session(LocalDateTime startDate, LocalDateTime endDate, SessionImage sessionImage, boolean isFree, SessionStatus sessionStatus, MaxEnrollment maxEnrollment, CurrentEnrollment currentEnrollment) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessionImage = sessionImage;
        this.isFree = isFree;
        this.sessionStatus = sessionStatus;
        this.maxEnrollment = maxEnrollment;
        this.currentEnrollment = currentEnrollment;
    }

    public boolean enroll(Enrollment enrollment) {
        if (sessionStatus != SessionStatus.OPEN) {
            throw new IllegalArgumentException("수강 신청 기간이 아닙니다.");
        }
        currentEnrollment.addStudent();
        return true;
    }

    public void startEnroll() {
        this.sessionStatus = SessionStatus.OPEN;
    }

    public void endEnroll() {
        this.sessionStatus = SessionStatus.CLOSED;
    }
}
