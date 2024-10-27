package nextstep.courses.domain;

import java.time.LocalDate;

public class Session {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private SessionType sessionType;
    private SessionStatus sessionStatus;

    public Session(Long id, String name, LocalDate startDate, LocalDate endDate, SessionType type, SessionStatus status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessionType = type;
        this.sessionStatus = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }
}
