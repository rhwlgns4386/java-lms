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

    public static Session createFree(String name, LocalDate startDate, LocalDate endDate, Image image) {
        return new Session(null, name, startDate, endDate, SessionType.FREE, SessionStatus.PREPARING, image, 0L, Long.MAX_VALUE);
    }

    public static Session createPaid(String name, LocalDate startDate, LocalDate endDate, Image image, Long price, Long students) {
        validatePaidSession(price, students);
        return new Session(null, name, startDate, endDate, SessionType.PAID, SessionStatus.PREPARING, image, price, students);
    }

    private static void validatePaidSession(Long price, Long students) {
        if(price == null || price <= 0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if(students == null || students <= 0){
            throw new IllegalArgumentException("Students must be greater than 0");
        }
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

    public Long getMaxStudents() {
        return maxStudents;
    }

    public Long getPrice() {
        return price;
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
