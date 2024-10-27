package nextstep.courses.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Session {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private Image image;
    private Long price;
    private Long maxStudents;

    public Session(Long id, String name, LocalDate startDate, LocalDate endDate, SessionType sessionType, SessionStatus sessionStatus, Image image, Long price, Long maxStudents) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.image = image;
        this.price = price;
        this.maxStudents = maxStudents;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(getId(), session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
