package nextstep.sessions.domain;

public class SessionStatus {

    private SessionStatusType status;
    private SessionProgressStatusType progressStatus;
    private SessionRecruitmentStatusType recruitmentStatus;

    // 기존 상태관리 방식 처리 기능
    public SessionStatus(String status) {
        this(status, null, null);
    }

    public SessionStatus(String progressStatus, String recruitmentStatus) {
        this(null, progressStatus,recruitmentStatus);
    }

    public SessionStatus(String status,  String progressStatus, String recruitmentStatus) {
        if (status == null && progressStatus == null && recruitmentStatus == null) {
            throw new RuntimeException("상태값이 누락되었습니다.");
        }
        if (status == null) {
            this.status = null;
            this.progressStatus = setProgressStatus(progressStatus);
            this.recruitmentStatus = setSessionRecruitmentStatusType(recruitmentStatus);
            return ;
        }
        this.status = SessionStatusType.getEnumByStatus(status);
        this.progressStatus = null;
        this.recruitmentStatus = null;
    }

    private static SessionProgressStatusType setProgressStatus(String progressStatus) {
        if (progressStatus == null || progressStatus.isEmpty()) {
            throw new RuntimeException("진행상태 값이 누락되었습니다");
        }
        return SessionProgressStatusType.getByStatusCode(progressStatus);
    }

    private static SessionRecruitmentStatusType setSessionRecruitmentStatusType(String recruitmentStatus) {

        if (recruitmentStatus == null || recruitmentStatus.isEmpty()) {
            throw new RuntimeException("보집상태 값이 누락되었습니다.");
        }
        return SessionRecruitmentStatusType.getByStatusCode(recruitmentStatus);
    }

    public static SessionStatus ofInitInstance() {
        return new SessionStatus(null,
                SessionProgressStatusType.PREPARING.getStatusCode(), SessionRecruitmentStatusType.NON_RECRUITING.getStatusCode());
    }

    public String getStatus() {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    public SessionProgressStatusType getProgressStatus() {
        if (progressStatus == null) {
            return null;
        }
        return progressStatus;
    }

    public SessionRecruitmentStatusType getRecruitmentStatus() {
        if (recruitmentStatus == null) {
            return null;
        }
        return recruitmentStatus;
    }

    public void isValidStatusForApplicationOld() {
        if (status.isStatusAvailableForApplication()) {
            return;
        }
        throw new RuntimeException("상태 : " + status + "수강이 불가한 상태입니다");
    }

    public void isValidStatusForApplication() {
        // 기존 데이터
        if(this.status != null) {
            isValidStatusForApplicationOld();
            return ;
        }
        if (recruitmentStatus == SessionRecruitmentStatusType.RECRUITING) {
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
        if (this.status != null) {
            return status == SessionStatusType.RECRUITING;
        }
        return recruitmentStatus == SessionRecruitmentStatusType.RECRUITING;
    }

    // 기존 데이터 or 신규 데이터 상태 변경(모집중으로 변경)
    public void startRecruiting() {
        if (isRecruiting()) {
            return ;
        }
        if (this.status != null) {
            this.status = SessionStatusType.RECRUITING;
            return ;
        }
        this.recruitmentStatus = SessionRecruitmentStatusType.RECRUITING;
    }

    public boolean isPreparing() {
        if (this.status == null) {
            return SessionProgressStatusType.PREPARING == this.progressStatus;
        }
        return status == SessionStatusType.PREPARING;
    }

}
