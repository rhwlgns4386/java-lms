package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionInfo {

    private Instructor instructor;

    private SessionMetaData sessionMetaData;

    private SessionPeriod sessionPeriod;

    private StateCode stateCode;

    private ProgressCode progressCode; //새로 추가됨

    public SessionInfo(SessionMetaData sessionMetaData, SessionPeriod sessionPeriod,
                       StateCode stateCode, ProgressCode progressCode, Instructor instructor) {
        if(sessionMetaData == null) {
            throw new IllegalArgumentException("강의 정보를 입력해주세요.");
        }
        if(sessionPeriod == null) {
            throw new IllegalArgumentException("강의 기간을 입력해주세요.");
        }
        if(stateCode == null) {
            throw new IllegalArgumentException("강의 상태를 입력해주세요.");
        }
        if(progressCode == null) {
            throw new IllegalArgumentException("강의 진행 상태를 입력해주세요.");
        }

        this.sessionPeriod = sessionPeriod;
        this.sessionMetaData =  sessionMetaData;
        this.stateCode = stateCode;
        this.progressCode = progressCode;
        this.instructor = instructor;
    }

    public String getTitle() {
        return sessionMetaData.getTitle();
    }

    public String getCreatorId() {
        return sessionMetaData.getCreatorId();
    }

    public LocalDateTime getApplyStartDate() {
        return sessionPeriod.getApplyStartDate();
    }

    public LocalDateTime getApplyEndDate() {
        return sessionPeriod.getApplyEndDate();
    }

    public void validateOrderSessionStatus() {
        stateCode.validateOrderSessionStatus();
    }

    public int getStatusCode() {
        return stateCode.getStatusCode();
    }

    public int getProgressCode() {
        return progressCode.getProgressCode();
    }

    public void validateOrderSessionProgressCode() {
        progressCode.validateOrderSessionProgressCode();
    }

    public long getInstructorId(){
        return instructor.getId();
    }
}
