package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private Long id;

    private final String title;

    private final Long creatorId;

    private final int cohort;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Session> sessions = new ArrayList<>();

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
        sessions.add(session);
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
        if (!sessions.contains(session)) {
            throw new IllegalStateException("해당 코스에 해당하는 강의가 아닙니다.");
        }
        return session.register(student, amount);
    }
}
