package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionInfo {
    private final long sessionId;

    SessionMetaData sessionMetaData;

    private SessionPeriod sessionPeriod;

    private StateCode stateCode;

    private ProgressCode progressCode; //새로 추가됨

    public SessionInfo(SessionMetaData sessionMetaData, SessionPeriod sessionPeriod,
                       StateCode stateCode, ProgressCode progressCode) {
        this(-1, sessionMetaData, sessionPeriod, stateCode, progressCode);
    }

    public SessionInfo(long sessionId, SessionMetaData sessionMetaData, SessionPeriod sessionPeriod,
                       StateCode stateCode, ProgressCode progressCode) {
        this.sessionId = sessionId;
        this.sessionPeriod = sessionPeriod;
        this.sessionMetaData =  sessionMetaData;
        this.stateCode = stateCode;
        this.progressCode = progressCode;
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

    public long getSessionId() {
        return sessionId;
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

}
