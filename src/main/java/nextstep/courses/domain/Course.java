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
    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Sessions sessions = new Sessions();

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

    public Payment processPayment(Student student, Long amount, Session session) {
        registerStudentInSession(student, session, amount);
        return processReceipt(student, session, amount);
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

    public Long getId() {
        return id;
    }

    public Sessions getSessions() {
        return new Sessions(sessions.clone());
    }

    public boolean contains(Session session) {
        return sessions.contains(session);
    }
    public int getSessionSize() {
        return sessions.size();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    public int hashCode() {
        return id.hashCode();
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

    private void registerStudentInSession(Student student, Session session, Long amount) {
        sessions.validateSession(session);
        session.register(student, amount);
    }

    private Payment processReceipt(Student student, Session session, Long amount) {
        return new Payment("0", this.id, session.getId(), student.getId(), amount);
    }
}
