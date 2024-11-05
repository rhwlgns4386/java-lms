package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Session {
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private SessionImage sessionImage;

    private boolean isFree;

    private SessionStatus sessionStatus;

    private int maxEnrollment;

    private int currentEnrollment;

    public Session(LocalDateTime startDate, LocalDateTime endDate, SessionImage sessionImage, boolean isFree, SessionStatus sessionStatus, int maxEnrollment, int currentEnrollment) {
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

        if (!isFree && currentEnrollment >= maxEnrollment) {
            throw new IllegalArgumentException("최대 수강신청 인원을 초과했습니다.");
        }

        currentEnrollment++;
        return true;
    }

    public void startEnroll() {
        this.sessionStatus = SessionStatus.OPEN;
    }

    public void endEnroll() {
        this.sessionStatus = SessionStatus.CLOSED;
    }
}
