package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionInfo {
    private String title;

    private LocalDateTime applyStartDate;

    private LocalDateTime applyEndDate;

    private long createId;

    public SessionInfo(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                       long createId) {
        this.title = title;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.createId = createId;
    }

    public LocalDateTime getApplyStartDate() {
        return applyStartDate;
    }

    public LocalDateTime getApplyEndDate() {
        return applyEndDate;
    }
}


