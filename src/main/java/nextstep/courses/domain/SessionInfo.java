package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionInfo {
    long sessionId;

    private String title;

    private SessionPeriod sessionPeriod;

    private String creatorId;

    public SessionInfo(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                       String creatorId) {
        this(-1, title, applyStartDate, applyEndDate, creatorId);
    }

    public SessionInfo(long sessionId, String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate, String creatorId) {
        this.sessionId = sessionId;
        this.title = title;
        this.sessionPeriod = new SessionPeriod(applyStartDate, applyEndDate);
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatorId() {
        return creatorId;
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
}


