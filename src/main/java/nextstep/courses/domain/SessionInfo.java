package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionInfo {
    private String title;

    private SessionPeriod sessionPeriod;

    private String creatorId;

    public SessionInfo(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                       String creatorId) {
        this.title = title;
        this.sessionPeriod = new SessionPeriod(applyStartDate, applyEndDate);
        this.creatorId = creatorId;
    }

}


