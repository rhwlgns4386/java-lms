package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.sessions.Session;
import nextstep.sessions.Sessions;
import nextstep.users.domain.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {
    private final String title;
    private final Long creatorId;
    private final int cohort;
    private Long id;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Sessions sessions = new Sessions(new ArrayList<>());

    public Course(String title, Long creatorId, int cohort) {
        this(0L, title, creatorId, cohort, LocalDateTime.now(), LocalDateTime.now());
    }

    public Course(Long id, String title, Long creatorId, int cohort, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.cohort = cohort;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getCohort() {
        return cohort;
    }

    public void addSession(Session session) {
        sessions.addSession(session);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorId=" + creatorId +
                ", cohort=" + cohort +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Payment processPayment(Student student, Long amount, Session session) {
        sessions.validateSession(session);
        session.register(student, amount);
        return processReceipt(student, amount);
    }

    private Payment processReceipt(Student student, Long amount) {
        return new Payment("0", this.id, student.getId(), amount);
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getCreatorId() {
        return creatorId;
    }
}
