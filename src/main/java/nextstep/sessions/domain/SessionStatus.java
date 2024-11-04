package nextstep.sessions.domain;

public class SessionStatus {

    private SessionStatusEnum status;
    private SessionProgressStatusEnum progressStatus;
    private SessionRecruitmentStatusEnum recruitmentStatus;


    public SessionStatus() {
        this(SessionStatusEnum.PREPARING, SessionProgressStatusEnum.PREPARING, SessionRecruitmentStatusEnum.NON_RECRUITING);
    }

    public SessionStatus(SessionProgressStatusEnum progressStatus, SessionRecruitmentStatusEnum recruitmentStatus) {
        this(null, progressStatus, recruitmentStatus);
    }

    public SessionStatus(SessionStatusEnum status) {
        this(status, null, null);
    }

    public SessionStatus(SessionStatusEnum status, SessionProgressStatusEnum progressStatus, SessionRecruitmentStatusEnum recruitmentStatus) {
        this.status = status;
        this.progressStatus = progressStatus;
        this.recruitmentStatus = recruitmentStatus;
    }


    public String getStatus() {
        return status.getValue();
    }

    public SessionProgressStatusEnum getProgressStatus() {
        return progressStatus;
    }

    public SessionRecruitmentStatusEnum getRecruitmentStatus() {
        return recruitmentStatus;
    }

//    public void isValidStatusForApplication() {
//        if (status.isStatusAvailableForApplication()) {
//            return;
//        }
//        throw new RuntimeException("상태 : " + status + "수강이 불가한 상태입니다");
//    }

    public void isValidStatusForApplication() {
        if (recruitmentStatus == SessionRecruitmentStatusEnum.RECRUITING) {
            return;
        }
        throw new RuntimeException("수강이 불가한 상태입니다");
    }


    @Override
    public boolean equals(Object obj) {
        SessionStatus target = (SessionStatus) obj;
        return target.getProgressStatus() == this.progressStatus && target.getRecruitmentStatus() == this.recruitmentStatus;
    }

    public boolean isRecruiting() {
        return recruitmentStatus == SessionRecruitmentStatusEnum.RECRUITING;
    }

    public SessionStatus startRecruiting() {
        if (isRecruiting()) {
            return this;
        }
        return new SessionStatus(SessionProgressStatusEnum.PROGRESSING, SessionRecruitmentStatusEnum.RECRUITING);
    }
}
